import { useEffect, useState, useRef } from "react";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import Cell from "./Cell";

const ChessBoard = () => {
  const [boardData, setBoardData] = useState([]);
  const [firstClick, setFirstClick] = useState(null);
  const [connected, setConnected] = useState(false);
  const stompClientRef = useRef(null);

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

  // 2️⃣ Setup WebSocket for moves
  useEffect(() => {
    const socket = new SockJS("http://localhost:8080/ws");
    const stompClient = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      debug: () => {},
    });

    stompClient.onConnect = () => {
      console.log("✅ STOMP Connected");
      setConnected(true);

      // Subscribe to board updates
      stompClient.subscribe("/topic/board", (message) => {
        setBoardData(JSON.parse(message.body));
      });
    };

    stompClient.activate();
    stompClientRef.current = stompClient;

    return () => {
      stompClientRef.current?.deactivate();
    };
  }, []);

  const handleClick = (row, col) => {
    if (!connected) return;

    const piece = boardData[row]?.[col];

    if (!firstClick && piece) {
      console.log("Colorrr: ", piece.color);
      setFirstClick({ row, col });
    } 
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
  };

  return (
    <div className="p-6">
      {boardData.map((row, i) => (
        <div key={i} className="flex">
          {row.map((piece, j) => (
            <Cell
              key={j}
              data={piece}
              className="hover:cursor-pointer"
              selected={firstClick?.row === i && firstClick?.col === j}
              onClick={() => handleClick(i, j)}
            />
          ))}
        </div>
      ))}
      <div>
        <button
          className="color bg-red-500 p-2.5 m-2.5"
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
