package Checkers;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for core (non-graphics) checkers logic.
 *
 * These tests avoid launching a Processing window (PApplet.runSketch),
 * and instead validate the underlying rules / data structures:
 * - move generation (diagonal step vs jump)
 * - capture behavior
 * - promotion to king enabling backwards movement
 */
public class SampleTest {

    /**
     * Helper: create an empty 8x8 board of Cells.
     */
    private static Cell[][] emptyBoard() {
        Cell[][] board = new Cell[App.BOARD_WIDTH][App.BOARD_WIDTH];
        for (int y = 0; y < App.BOARD_WIDTH; y++) {
            for (int x = 0; x < App.BOARD_WIDTH; x++) {
                board[y][x] = new Cell(x, y);
            }
        }
        return board;
    }

    /**
     * Helper: place a piece at (x,y) on the board (and set its position).
     */
    private static CheckersPiece place(Cell[][] board, int x, int y, char colour) {
        CheckersPiece p = new CheckersPiece(colour);
        board[y][x].setPiece(p);
        return p;
    }

    // -----------------------
    // Actual tests start here
    // -----------------------

    // Test that a white piece in the middle of an empty board has two simple diagonal moves (down-left/down-right).
    @Test
    public void testAvailableMoves_WhiteSimpleDiagonals() {
        Cell[][] board = emptyBoard();
        CheckersPiece w = place(board, 3, 3, 'w'); // centre-ish

        Set<Move> moves = w.getAvailableMoves(board);

        // From (3,3), white moves "down" (y+1) to (2,4) and (4,4) if empty.
        assertEquals(2, moves.size(), "White piece should have exactly 2 simple diagonal moves on an empty board.");
        assertTrue(moves.contains(new Move(board[4][2], null)), "Expected move to (2,4).");
        assertTrue(moves.contains(new Move(board[4][4], null)), "Expected move to (4,4).");
    }

    // Test that a black piece in the middle of an empty board has two simple diagonal moves (up-left/up-right).
    @Test
    public void testAvailableMoves_BlackSimpleDiagonals() {
        Cell[][] board = emptyBoard();
        CheckersPiece b = place(board, 3, 4, 'b'); // centre-ish

        Set<Move> moves = b.getAvailableMoves(board);

        // From (3,4), black moves "up" (y-1) to (2,3) and (4,3) if empty.
        assertEquals(2, moves.size(), "Black piece should have exactly 2 simple diagonal moves on an empty board.");
        assertTrue(moves.contains(new Move(board[3][2], null)), "Expected move to (2,3).");
        assertTrue(moves.contains(new Move(board[3][4], null)), "Expected move to (4,3).");
    }

    // Test that if a piece is diagonally adjacent, a jump move (2 cells) is offered (even over own piece).
    // (Per assignment warmup spec: 'Pieces can jump over their own pieces and opponent’s pieces'.)
    @Test
    public void testAvailableMoves_JumpOfferedWhenAdjacentPiecePresent() {
        Cell[][] board = emptyBoard();

        // White piece at (2,2). Put ANY piece at (3,3) to enable jump to (4,4) if empty.
        CheckersPiece w = place(board, 2, 2, 'w');
        place(board, 3, 3, 'w'); // own piece adjacent
        // landing cell (4,4) is empty by default

        Set<Move> moves = w.getAvailableMoves(board);

        assertTrue(moves.contains(new Move(board[4][4], board[3][3].getPiece())),
                "Expected a jump move landing at (4,4) capturing the jumped piece reference (even if own piece).");
    }

    // Test that executing a move with a captured piece marks that piece as captured and moves the mover.
    @Test
    public void testMoveExecute_CapturesAndMovesPiece() {
        Cell[][] board = emptyBoard();

        // Mover at (2,2), jumped piece at (3,3), landing at (4,4)
        CheckersPiece mover = place(board, 2, 2, 'w');
        CheckersPiece jumped = place(board, 3, 3, 'b');

        Move jumpMove = new Move(board[4][4], jumped);
        jumpMove.execute(mover);

        assertTrue(jumped.isCaptured(), "Jumped piece should be marked captured after executing capture move.");
        assertSame(mover, board[4][4].getPiece(), "Mover should end up on destination cell after executing move.");
        assertNull(board[2][2].getPiece(), "Original cell should be empty after the mover leaves.");
    }

    // Test that promotion to king enables backwards movement.
    @Test
    public void testPromotion_AllowsBackwardsMoves() {
        Cell[][] board = emptyBoard();

        // Put a white piece on the last row and call checkPromotion -> should become king internally.
        CheckersPiece w = place(board, 3, 7, 'w');
        w.checkPromotion();

        // Now, as a king, it can move "up" as well (y-1).
        // From (3,7) possible backwards moves are (2,6) and (4,6) on an empty board.
        Set<Move> moves = w.getAvailableMoves(board);

        assertTrue(moves.contains(new Move(board[6][2], null)), "King should be able to move backwards to (2,6).");
        assertTrue(moves.contains(new Move(board[6][4], null)), "King should be able to move backwards to (4,6).");
    }

}

// gradle run                        Run the program
// gradle test                       Run the testcases

// Please ensure you leave a comments in your testcases explaining what the testcase is testing.
// Your mark will be based off the average of branches and instructions code coverage.
// To run the testcases and generate the jacoco code coverage report: 
// gradle test jacocoTestReport
