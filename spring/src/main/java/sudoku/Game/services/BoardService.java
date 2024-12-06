package sudoku.Game.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sudoku.Game.dtos.BoardDTO;
import sudoku.Game.entities.Sudoku;
import sudoku.Game.mappers.BoardMapper;

@Service
public class BoardService {

    @Autowired
    private BoardMapper boardMapper;

    public BoardDTO createBoard(int difficulty) {
        Sudoku sudoku = new Sudoku(9, difficulty);

        sudoku.createBoard();

        String[][] board = boardMapper.intToStringArray(sudoku.getBoard());
        String[][] solvedBoard = boardMapper.intToStringArray(sudoku.getSolvedBoard());

        BoardDTO boardDTO = new BoardDTO(null, null, board, solvedBoard);

        return boardDTO;
    }

}
