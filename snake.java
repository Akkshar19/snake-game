	import java.util.*;

	public class snakegame {
	    private static final int BOARD_SIZE = 10;
	    private static final char EMPTY_CELL = '.';
	    private static final char SNAKE_CELL = 'O';
	    private static final char FOOD_CELL = 'F';

	    private LinkedList<Point> snake;
	    private Point food;
	    private Direction direction;
	    private boolean gameOver;

	    private enum Direction {
	        UP, DOWN, LEFT, RIGHT
	    }

	    private static class Point {
	        int x, y;

	        Point(int x, int y) {
	            this.x = x;
	            this.y = y;
	        }
	    }

	    public snakegame() {
	        snake = new LinkedList<>();
	        snake.add(new Point(BOARD_SIZE / 2, BOARD_SIZE / 2));
	        direction = Direction.RIGHT;
	        placeFood();
	        gameOver = false;
	    }

	    private void move() {
	        Point head = snake.getFirst();
	        Point newHead;

	        switch (direction) {
	            case UP:
	                newHead = new Point(head.x, (head.y - 1 + BOARD_SIZE) % BOARD_SIZE);
	                break;
	            case DOWN:
	                newHead = new Point(head.x, (head.y + 1) % BOARD_SIZE);
	                break;
	            case LEFT:
	                newHead = new Point((head.x - 1 + BOARD_SIZE) % BOARD_SIZE, head.y);
	                break;
	            case RIGHT:
	                newHead = new Point((head.x + 1) % BOARD_SIZE, head.y);
	                break;
	            default:
	                return;
	        }

	        if (newHead.x == food.x && newHead.y == food.y) {
	            snake.addFirst(newHead);
	            snake.removeLast();
	            placeFood();

	            gameOver = true;
	        } else {
	            snake.addFirst(newHead);
	            snake.removeLast();
	        }
	    }

	    private void placeFood() {
	        Random random = new Random();
	        int x, y;
	        do {
	            x = random.nextInt(BOARD_SIZE);
	            y = random.nextInt(BOARD_SIZE);
	        } while (isSnakeCell(x, y));

	        food = new Point(x, y);
	    }

	    private boolean isSnakeCell(int x, int y) {
	        return snake.stream().anyMatch(point -> point.x == x && point.y == y);
	    }

	    private void checkCollision() {
	        Point head = snake.getFirst();

	        // Check if snake collides with itself
	        if (snake.stream().skip(1).anyMatch(point -> point.x == head.x && point.y == head.y)) {
	            printBoard();
	            gameOver = true;
	        }
	    }

	    private void printBoard() {
	        char[][] board = new char[BOARD_SIZE][BOARD_SIZE];

	        for (int i = 0; i < BOARD_SIZE; i++) {
	            for (int j = 0; j < BOARD_SIZE; j++) {
	                board[i][j] = EMPTY_CELL;
	            }
	        }

	        snake.forEach(point -> board[point.y][point.x] = SNAKE_CELL);
	        board[food.y][food.x] = FOOD_CELL;

	        for (int i = 0; i < BOARD_SIZE; i++) {
	            for (int j = 0; j < BOARD_SIZE; j++) {
	                System.out.print(board[i][j] + " ");
	            }
	            System.out.println();
	        }
	    }

	    public void play() {
	        Scanner scanner = new Scanner(System.in);

	        while (!gameOver) {
	            printBoard();
	            System.out.print("Enter direction (W/A/S/D): ");
	            char input = scanner.next().toUpperCase().charAt(0);

	            switch (input) {
	                case 'W':
	                    direction = Direction.UP;
	                    break;
	                case 'A':
	                    direction = Direction.LEFT;
	                    break;
	                case 'S':
	                    direction = Direction.DOWN;
	                    break;
	                case 'D':
	                    direction = Direction.RIGHT;
	                    break;
	                default:
	                    System.out.println("Invalid input. Use W/A/S/D to move.");
	                    continue;
	            }

	            move();
	            checkCollision();
	        }

	        System.out.println("Game over!");
	        scanner.close();
	    }

	    public static void main(String[] args) {
	        System.out.println("Hello World");
	        snakegame game = new snakegame();
	        game.play();
	    }
	}
	
