const express = require("express");
const app = express();
const http = require("http");
const { Server } = require("socket.io");
const cors = require("cors");
const axios = require("axios");

app.use(cors());

const server = http.createServer(app);

const io = new Server(server, {
  cors: {
    origin: [
      "http://localhost:3000",
      "http://192.168.28.122:3000",
      "http://10.58.83.161:3000",
      "http://192.168.1.107:3000",
      "http://192.168.1.106:3000",
      "http://172.31.6.155:3000",
    ],
    methods: ["GET", "POST"],
  },
});

//const axiosService = axios.create({ baseURL: "http://localhost:8080" });
//const axiosService = axios.create({ baseURL: "http://192.168.28.122:8080" });
//const axiosService = axios.create({ baseURL: "http://192.168.1.107:8080" });
// const axiosService = axios.create({ baseURL: "http://192.168.1.106:8080" });
const axiosService = axios.create({ baseURL: "http://172.31.6.155:8080" });

http: io.on("connection", (socket) => {
  console.log(`User "${socket.id}" connected`);

  socket.on("join_room", (room) => {
    var isUserJoined = false;

    if (io.sockets.adapter.rooms.get(room)) {
      if (io.sockets.adapter.rooms.get(room).size < 2) {
        socket.join(room);
        isUserJoined = true;
      }
    } else {
      socket.join(room);
      isUserJoined = true;
    }

    if (isUserJoined) {
      console.log(
        `User with id "${socket.id}" has joined the room "${room}" that has "${
          io.sockets.adapter.rooms.get(room).size
        }" users`
      );

      io.to(room).emit("test_message", "test_message");
    }
  });

  socket.on("init_game", async (data) => {
    try {
      const response = await axiosService.post(
        "/api/game/init?room=" + data.room,
        null,
        {
          headers: {
            Authorization: "Bearer " + data.accessToken,
          },
        }
      );

      io.to();
      io.to(data.room).emit("board", response.data);
    } catch (error) {
      console.log(error);
    }
  });

  socket.on("init_quick_game", async (data) => {
    try {
      const response = await axiosService.post(
        "/api/game/quick?room=" + data.room,
        null
      );

      io.to(data.room).emit("quick_board", response.data);
    } catch (error) {
      console.log(error);
    }
  });

  socket.on("get_players_info", (data) => {
    io.to(data.room).emit("player_info", data);
  });

  // Bu kısım chat özelliği geldiğinde ayarlanacak İnşaAllah
  // socket.on("chat_init", (data) => {
  //   socket.join(data.room);

  //   const userData = {
  //     userId: socket.id,
  //     username: data.username,
  //   };

  //   socket.to(data.room).emit("emit_user", userData);

  //   console.log(
  //     `User "${data.username}" with id "${socket.id}" has joined the room "${
  //       data.room
  //     }" that has "${io.sockets.adapter.rooms.get(data.room).size}" users`
  //   );
  // });

  // socket.on("send_message", (data) => {
  //   const messageUserCountPair = {
  //     messageWithSender: data.messageWithSender,
  //     userAmount: io.sockets.adapter.rooms.get(data.room).size,
  //   };

  //   socket.to(data.room).emit("receive_message", messageUserCountPair);

  //   console.log(
  //     `User "${data.messageWithSender.sender}" send a message : "${data.messageWithSender.message}" to the room "${data.room}"`
  //   );
  // });
});

server.listen(3001, () => {
  console.log("Server is running");
});
