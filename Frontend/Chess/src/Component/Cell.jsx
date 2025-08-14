const Cell = ({ data, selected, hint,onClick }) => {
  const pieces = {
    pawn:   { white: "♙", black: "♟" },
    rook:   { white: "♖", black: "♜" },
    knight: { white: "♘", black: "♞" },
    bishop: { white: "♗", black: "♝" },
    queen:  { white: "♕", black: "♛" },
    king:   { white: "♔", black: "♚" }
  };

  // console.log("Hintsssssssssss: "+hints);
  
  const pieceType = data?.type?.toLowerCase();
  const pieceColor = data?.color?.toLowerCase();
  const row = data?.row ?? 0;
  const col = data?.col ?? 0;

  const isDarkSquare = (row + col) % 2 === 1;
  const bgColor = isDarkSquare ? "bg-gray-500" : "bg-gray-200";
  const borderColor = isDarkSquare ? "#000" : "#fff";

  const hintColor = hint? "bg-green-300" : "";

  return (
    <div
      className={`flex w-[60px] h-[60px] border-2 justify-center items-center 
        ${bgColor} ${selected ? "bg-green-400" : ""} ${bgColor} ${hintColor}`}
      style={{ borderColor }}
      onClick={onClick}
    >
      {pieceType && pieceColor && (
        <span
          className="text-5xl"
          style={{
            fontFamily: `"Segoe UI Symbol", "Arial Unicode MS", sans-serif`,
            color: pieceColor === "white" ? "#fff" : "#000",
            textShadow:
              pieceColor === "white"
                ? "1px 1px 2px black"
                : "1px 1px 2px white",
          }}
        >
          {pieces[pieceType][pieceColor] || ""}
        </span>
      )}
    </div>
  );
};

export default Cell;
