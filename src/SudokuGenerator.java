import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class SudokuGenerator {
    private int[] sudoku = new int[81];
    private String level;

    public SudokuGenerator(String level) {
        this.level = level;
    }

    private void createTemplate() throws IllegalArgumentException{
            for (int i = 0; i < 81; i++) {
                sudoku[i] = insertNumber(i);
            }
    }

    private int insertNumber(int index) {
        if (index == 0) {
            return ThreadLocalRandom.current().nextInt(1,10);
        }
        Set<Integer> oneToNine = new HashSet<>(9);
        for (int i = 1; i < 10; i++) {
            oneToNine.add(i);
        }
        oneToNine.removeAll(usedFromHorizontal(index));
        oneToNine.removeAll(usedFromVertical(index));
        oneToNine.removeAll(usedFromBlock(index));
        Object[] validNumbers = oneToNine.toArray();
        return (int) validNumbers[ThreadLocalRandom.current().nextInt(0, validNumbers.length)];
    }

    private Set<Integer> usedFromHorizontal(int index) {
        if (index % 9 == 0) {
            return new HashSet<>();
        }
        HashSet<Integer> usedNumbers = new HashSet<>();
        for (int i = 0; i < index % 9; i++) {
            usedNumbers.add(sudoku[index - 1 - i]);
        }
        return usedNumbers;
    }

    private Set<Integer> usedFromVertical(int index) {
        if (index < 9) {
            return new HashSet<>();
        }
        HashSet<Integer> usedNumbers = new HashSet<>();
        for (int i = 0; i < index / 9; i++) {
            usedNumbers.add(sudoku[index - (i + 1) * 9]);
        }
        return usedNumbers;
    }

    private Set<Integer> usedFromBlock(int index) {
        int horizonBlock = index % 9 / 3;
        int verticalBlock = index / 27;
        int upperLeftIndex = horizonBlock * 3 + verticalBlock * 27;
        Set<Integer> usedNumbers = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int currentIndex = upperLeftIndex + (i * 9) + j;
                if (currentIndex == index) {
                    usedNumbers.add(sudoku[currentIndex]);
                }

            }
        }
        usedNumbers.remove(0);
        return usedNumbers;


    }

  public static void main(String[] args) {
        SudokuGenerator sudokuGenerator = new SudokuGenerator("");
        boolean flag = true;
        int count = 0;
        while (flag) {
            try {
                sudokuGenerator.createTemplate();
                flag = false;
            } catch (IllegalArgumentException e) {
                count++;
            }
        }
        for (int i = 0; i < 81; i++) {
            System.out.print(sudokuGenerator.sudoku[i] + " ");
            if (i % 9 == 8) {
                System.out.println();
            }
        }
        System.out.println(count);



  }
}
