import express from "express";
import { io } from "socket.io-client";

const socketio = io("http://localhost:3001");

const server = express();

server.use(express.json());

server.get("/", (req, res) => {
  res.send("Welcome to my server!");
});

server.post("/game/start", (req, res) => {
  const { room } = req.body;
  const { board } = req.body;

  res.send("Request success");

  const data = {
    room: room,
    board: board,
  };

  console.log(room);
  console.log(board);

  socketio.emit("start_game", data);
});

server.listen(3002, () => {
  console.log(`Server is running on port 3002`);
});
