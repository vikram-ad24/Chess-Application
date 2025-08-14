import { useEffect, useState, useRef } from "react";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import Cell from "./Cell";

const ChessBoard = () => {
  const [boardData, setBoardData] = useState([]);
  const [firstClick, setFirstClick] = useState(null);
  const [connected, setConnected] = useState(false);
  const [hints, setHints] = useState([]); // store hint positions
  const stompClientRef = useRef(null);

  const columnLabels = ["A", "B", "C", "D", "E", "F", "G", "H"];

  // 1️⃣ Fetch initial board via HTTP
  useEffect(() => {
    const fetchBoard = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/board");
        if (!response.ok) {
          throw new Error("Failed to fetch board data");
        }
        const data = await response.json();
        setBoardData(data);
      } catch (error) {
        console.error("Error fetching board data:", error);
      }
    };
    fetchBoard();
  }, []);

  // 2️⃣ Setup WebSocket for moves and hints
  useEffect(() => {
    const socket = new SockJS("http://localhost:8080/ws");
    const stompClient = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      debug: () => { },
    });

    stompClient.onConnect = () => {
      console.log("✅ STOMP Connected");
      setConnected(true);

      // Subscribe to board updates
      stompClient.subscribe("/topic/board", (message) => {
        setBoardData(JSON.parse(message.body));
      });

      // Subscribe to hints updates
      stompClient.subscribe("/topic/hints", (message) => {
        setHints(JSON.parse(message.body)); // expecting List<int[]> from backend

        console.log("Received hints:", JSON.parse(message.body));
      });
    };

    stompClient.activate();
    stompClientRef.current = stompClient;

    return () => {
      stompClientRef.current?.deactivate();
    };
  }, []);

  const handleClick = (row, col) => {
    console.log(`Clicked cell at (${row}, ${col})`);
    if (!connected) return;

    const piece = boardData[row]?.[col];

    // First click → select piece & request hints
    if (!firstClick && piece) {
      console.log("Selected piece color:", piece.color);
      setFirstClick({ row, col });

      // Request hints from backend
      stompClientRef.current.publish({
        destination: "/app/hints",
        body: JSON.stringify({
          fromRow: row,
          fromColumn: col
        }) // dynamic position
      });

    }
    // Second click → make move
    else if (firstClick) {
      stompClientRef.current.publish({
        destination: "/app/chess",
        body: JSON.stringify({
          fromRow: firstClick.row,
          fromColumn: firstClick.col,
          toRow: row,
          toColumn: col,
          color: piece?.color,
        }),
      });

      setFirstClick(null);
      setHints([]); // clear hints after move
    }
  };

  const handleReset = () => {
    console.log("Resetting the board...");
    if (!connected) {
      console.warn("⚠️ STOMP not connected yet!");
      return;
    }
    stompClientRef.current.publish({
      destination: "/app/reset",
      body: "",
    });
    setHints([]);
    setFirstClick(null);
  };

  return (
    <div className="p-6">
      {/* Column labels top */}
      <div className="flex ml-8">
        {columnLabels.map((label, i) => (
          <div key={i} className="w-12 h-12 m-1.5 flex items-center justify-center font-bold">
            {label}
          </div>
        ))}
      </div>

      {boardData.map((row, i) => (
        <div key={i} className="flex">
          {/* Row number */}
          <div className="w-8 h-12 flex items-center justify-center font-bold">
            {8 - i}
          </div>

          {row.map((piece, j) => {
            const isSelected = firstClick?.row === i && firstClick?.col === j;
            const isHint = hints.some(([hintRow, hintCol]) => hintRow === i && hintCol === j);

            return (
              <Cell
                key={j}
                data={{ ...piece, row: i, col: j }}
                className="hover:cursor-pointer"
                selected={isSelected}
                hint={isHint}
                onClick={() => handleClick(i, j)}
              />
            );
          })}
        </div>
      ))}

      {/* Bottom column labels */}
      <div className="flex ml-8">
        {columnLabels.map((label, i) => (
          <div key={i} className="w-12 h-12 m-1.5 flex items-center justify-center font-bold">
            {label}
          </div>
        ))}
      </div>

      <div>
        <button
          className="bg-red-500 text-white p-2.5 m-2.5"
          onClick={handleReset}
          disabled={!connected}
        >
          Reset
        </button>
      </div>
    </div>
  );
};

export default ChessBoard;
