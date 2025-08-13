const Cell = ({ data,selected, onClick }) => {

    // console.log(data?.type);

const pieces = {
  pawn:   { white: "♙", black: "♟" },
  rook:   { white: "♖", black: "♜" },
  knight: { white: "♘", black: "♞" },
  bishop: { white: "♗", black: "♝" },
  queen:  { white: "♕", black: "♛" },
  king:   { white: "♔", black: "♚" }
};

  const pieceType = data?.type?.toLowerCase();
  const pieceColor = data?.color?.toLowerCase();

  // Chessboard square color: dark/light alternating
  // const isDarkSquare = (row + col) % 2 === 1;
  // const squareColor = isDarkSquare ? "bg-gray-500" : "bg-gray-200";

  return (
    <div
      className={`flex w-[60px] h-[60px] border border-solid justify-center items-center 
         ${selected ? "bg-green-400" : ""}`}
      onClick={onClick}
    >
      <h1 className="text-5xl">
        {pieces[pieceType]?.[pieceColor] || ""}
      </h1>
    </div>
  );
}


export default Cell;
