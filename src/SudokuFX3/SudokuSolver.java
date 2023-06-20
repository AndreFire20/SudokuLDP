/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuFX3;

/**
 *
 * @author Utilizador
 */

public class SudokuSolver {
    private int[][] grid;

    public SudokuSolver(int[][] initialGrid) {
        this.grid = initialGrid;
    }

    public boolean solve() {
        return solve(0, 0);
    }

    private boolean solve(int row, int col) {
        if (col == grid.length) {
            col = 0;
            row++;
            if (row == grid.length) {
                return true;
            }
        }
        if (grid[row][col] != 0) {
            return solve(row, col + 1);
        }
        for (int val = 1; val <= grid.length; val++) {
            if (isValueValid(row, col, val)) {
                grid[row][col] = val;
                if (solve(row, col + 1)) {
                    return true;
                }
            }
        }
        grid[row][col] = 0;
        return false;
    }

    private boolean isValueValid(int row, int col, int val) {
        for (int i = 0; i < grid.length; i++) {
            if (grid[row][i] == val) {
                return false;
            }
            if (grid[i][col] == val) {
                return false;
            }
        }
        int regionSize = (int) Math.sqrt(grid.length);
        int regionRow = row / regionSize;
        int regionCol = col / regionSize;
        for (int i = regionRow * regionSize; i < (regionRow + 1) * regionSize; i++) {
            for (int j = regionCol * regionSize; j < (regionCol + 1) * regionSize; j++) {
                if (grid[i][j] == val) {
                    return false;
                }
            }
        }
        return true;
    }

    public int[][] getGrid() {
        return grid;
    }
}