/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SudokuFX3;

import java.util.Random;


public class SudokuGrid {
    private final int SIZE = 9;
    private int[][] grid;
    
    public SudokuGrid() {
        grid = new int[SIZE][SIZE];
        generateInitialGrid();
    }

   public void generateInitialGrid() {
        // Cria um grid resolvido
        int[][] solvedGrid = new int[9][9];
        SudokuSolver solver = new SudokuSolver(solvedGrid);
        solver.solve();
        
        // Remove alguns números aleatoriamente para criar um grid inicial
        Random rand = new Random();
        for (int i = 0; i < 45; i++) {
            int row = rand.nextInt(9);
            int col = rand.nextInt(9);
            solvedGrid[row][col] = 0;
        }
        
        // Copia o grid inicial para a grade atual
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = solvedGrid[i][j];
            }
        }
    }
    public int getValue(int row, int col) {
        return grid[row][col];
    }
    
    public void setValue(int row, int col, int value) {
        grid[row][col] = value;
    }
    
    public boolean isValueValid(int row, int col, int value) {
        // Verifica se o valor já existe na linha ou coluna
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == value || grid[i][col] == value) {
                return false;
            }
        }
        
        // Verifica se o valor já existe no bloco 3x3
        int blockRow = row / 3 * 3;
        int blockCol = col / 3 * 3;
        for (int i = blockRow; i < blockRow + 3; i++) {
            for (int j = blockCol; j < blockCol + 3; j++) {
                if (grid[i][j] == value) {
                    return false;
                }
            }
        }
        
        // Se o valor não viola nenhuma regra, retorna verdadeiro
        return true;
    }

 
}