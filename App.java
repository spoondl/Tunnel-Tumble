import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.Map;

/*
class Riddle {
    String question;
    String[] options;
    String correctAnswer;

    public Riddle(String question, String[] options, String correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Riddle: ").append(question).append("\n");
        for (int i = 0; i < options.length; i++) {
            sb.append((char) ('A' + i)).append(". ").append(options[i]).append("\n");
        }
        sb.append("Correct Answer: ").append(correctAnswer);
        return sb.toString();
    }
}

class ChatGPTRiddle {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "YOUR_OPENAI_API_KEY";  // Replace with your actual API key

    public static Riddle getRiddle() {
        try {
            // Define the request payload
            String prompt = "Generate a riddle for a middle schooler. Provide the riddle question, four possible answers, and specify which one is correct. Return the response as a structured JSON:\n"
                          + "{ 'question': '...', 'options': ['A', 'B', 'C', 'D'], 'answer': 'Correct Answer' }.";

            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "gpt-4-turbo");
            requestBody.put("messages", new JSONArray()
                .put(new JSONObject().put("role", "system").put("content", "You are a riddle master for middle schoolers."))
                .put(new JSONObject().put("role", "user").put("content", prompt))
            );
            requestBody.put("max_tokens", 200);

            // Make the API request
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = requestBody.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Read the response
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            // Parse JSON response
            JSONObject jsonResponse = new JSONObject(response.toString());
            String content = jsonResponse.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
            JSONObject riddleJson = new JSONObject(content);

            // Extract data
            String question = riddleJson.getString("question");
            JSONArray optionsJson = riddleJson.getJSONArray("options");
            String[] options = new String[optionsJson.length()];
            for (int i = 0; i < optionsJson.length(); i++) {
                options[i] = optionsJson.getString(i);
            }
            String correctAnswer = riddleJson.getString("answer");

            return new Riddle(question, options, correctAnswer);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
*/

public class App extends JPanel {
    static final int boardSize = 10;
    static final int tileSize = 70;
    static int userTemp = 1; // Player starts at position 1 (bottom-left)
    static int compUser = 1; // Computer starts at position 1 (bottom-left)
    static Random random = new Random();

    // Array to store positions of mine and tunnel tiles
    /*
    static final int[] snakePositions = new int[random.nextInt(5,10)];
    for (int i=0; i<snakePositions.length; i++) {
        snakePositions[i] = random.nextInt(10,90);
    }
    static final int[] tunnelPositions = new int[random.nextInt(5,10)];
    */
    static final int[] snakePositions = {32,50,65,75,95};
    static final int[] tunnelPositions = {4,
        9,
        21,
        28,
        51,
        72,
        80
    };

    static final String[][] questions = {
        {
            "What is the capital of France?",
            "Paris",
            "London",
            "Berlin",
            "Madrid",
            "Paris"
        },
        {
            "What is the boiling point of water?",
            "90°C",
            "100°C",
            "110°C",
            "120°C",
            "100°C"
        },
        {
            "Which planet is known as the Red Planet?",
            "Earth",
            "Mars",
            "Jupiter",
            "Venus",
            "Mars"
        },
        {
            "Who wrote 'Romeo and Juliet'?",
            "William Shakespeare",
            "Mark Twain",
            "Charles Dickens",
            "Jane Austen",
            "William Shakespeare"
        },
        {
            "What is the largest mammal?",
            "Elephant",
            "Whale",
            "Shark",
            "Giraffe",
            "Whale"
        },
        {
            "What is the square root of 64?",
            "6",
            "8",
            "10",
            "12",
            "8"
        },
        {
            "How many continents are there?",
            "5",
            "6",
            "7",
            "8",
            "7"
        },
        {
            "Which element's symbol is 'O'?",
            "Oxygen",
            "Osmium",
            "Ozone",
            "Oganesson",
            "Oxygen"
        },
        {
            "What is 10 + 15?",
            "20",
            "25",
            "30",
            "35",
            "25"
        },
        {
            "Who was the first president of the United States?",
            "George Washington",
            "Thomas Jefferson",
            "Abraham Lincoln",
            "John Adams",
            "George Washington"
        },
        {
            "Which animal is known for its ability to change color?",
            "Chameleon",
            "Lizard",
            "Snake",
            "Frog",
            "Chameleon"
        },
        {
            "What is the largest planet in our solar system?",
            "Earth",
            "Jupiter",
            "Saturn",
            "Neptune",
            "Jupiter"
        },
        {
            "Which of these is a primary color?",
            "Green",
            "Blue",
            "Purple",
            "Orange",
            "Blue"
        },
        {
            "What is 5 times 6?",
            "30",
            "25",
            "35",
            "40",
            "30"
        },
        {
            "Which country is known for the Great Wall?",
            "China",
            "India",
            "Japan",
            "Russia",
            "China"
        },
        {
            "What is the symbol for gold?",
            "Au",
            "Ag",
            "Pb",
            "Fe",
            "Au"
        },
        {
            "What gas do plants absorb from the air?",
            "Oxygen",
            "Nitrogen",
            "Carbon Dioxide",
            "Hydrogen",
            "Carbon Dioxide"
        },
        {
            "Who painted the Mona Lisa?",
            "Pablo Picasso",
            "Leonardo da Vinci",
            "Vincent van Gogh",
            "Claude Monet",
            "Leonardo da Vinci"
        },
        {
            "Which planet is closest to the Sun?",
            "Venus",
            "Earth",
            "Mercury",
            "Mars",
            "Mercury"
        },
        {
            "What is the capital of the United States?",
            "New York",
            "Washington, D.C.",
            "Los Angeles",
            "Chicago",
            "Washington, D.C."
        },
        {
            "What is the chemical formula for water?",
            "CO2",
            "H2O",
            "O2",
            "H2",
            "H2O"
        },
        {
            "Which ocean is the largest?",
            "Atlantic",
            "Pacific",
            "Indian",
            "Arctic",
            "Pacific"
        },
        {
            "What is 100 divided by 5?",
            "10",
            "15",
            "20",
            "25",
            "20"
        },
        {
            "Which number is a prime number?",
            "9",
            "15",
            "17",
            "20",
            "17"
        },
        {
            "What is the freezing point of water?",
            "0°F",
            "0°C",
            "-1°C",
            "32°F",
            "0°C"
        },
        {
            "Which of these is a desert?",
            "Amazon",
            "Sahara",
            "Forest",
            "Jungle",
            "Sahara"
        },
        {
            "How many days are in a year?",
            "360",
            "365",
            "366",
            "364",
            "365"
        },
        {
            "Who invented the telephone?",
            "Thomas Edison",
            "Nikola Tesla",
            "Alexander Graham Bell",
            "Isaac Newton",
            "Alexander Graham Bell"
        },
        {
            "What is the largest ocean?",
            "Atlantic",
            "Pacific",
            "Indian",
            "Southern",
            "Pacific"
        },
        {
            "Which of these is a fruit?",
            "Carrot",
            "Potato",
            "Tomato",
            "Cucumber",
            "Tomato"
        },
        {
            "Which country is the Eiffel Tower in?",
            "Italy",
            "France",
            "Spain",
            "Germany",
            "France"
        },
        {
            "What is the tallest mountain in the world?",
            "K2",
            "Everest",
            "Kangchenjunga",
            "Makalu",
            "Everest"
        }
    };
    BufferedImage player1, Comp;

    URL player1URL, compURL;

    public App() {
        try {
            player1URL = new URI("https://static.platform.michaels.com/2c-prd/61756367773152.jpeg").toURL();
            compURL = new URI("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSbfRfEPJG2DRSnFzWNygT8_KkUiUpYCd9U-w&s").toURL();
            player1 = ImageIO.read(player1URL);
            Comp = ImageIO.read(compURL);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setPreferredSize(new Dimension(boardSize * tileSize, boardSize * tileSize));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                int x = col * tileSize;
                int y = row * tileSize;

                // Calculate the position number for this tile
                int position = (9 - row) * 10 + col + 1;

                // Set the tile color
                if (isSnake(position)) {
                    g.setColor(Color.RED);
                } else if (istunnel(position)) {
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor(Color.WHITE);
                }

                // Draw the tile
                g.fillRect(x, y, tileSize, tileSize);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, tileSize, tileSize);

                // Draw position number (optional)
                g.drawString(String.valueOf(position), x + 5, y + 15);
            }
        }

        if (player1 != null) { //if the player is defined
            drawPiece(g, userTemp, player1);
        }

        if (Comp != null) {
            drawPiece(g, compUser, Comp);
        }
    }

    public static boolean askRiddle() {
        Random random = new Random();
        int randomQuestion = random.nextInt(0, questions.length);
        String displayString = "Your question is: " + questions[randomQuestion][0] +
            "\nA. " + questions[randomQuestion][1] +
            "\nB. " + questions[randomQuestion][2] +
            "\nC. " + questions[randomQuestion][3] +
            "\nD. " + questions[randomQuestion][4];
        String answer = JOptionPane.showInputDialog(displayString);
        if (answer.equalsIgnoreCase(questions[randomQuestion][5])) {
            return true;
        }
        return false;
    }

    public void drawPiece(Graphics g, int position, BufferedImage image) {
        position--;
        // Convert position to grid coordinates
        int row = 9 - (position / 10);
        int col = position % 10;

        int x = col * tileSize;
        int y = row * tileSize;

        // Maintain aspect ratio while fitting within tileSize x tileSize
        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();
        double aspectRatio = (double) imgWidth / imgHeight;

        int drawWidth, drawHeight;
        if (aspectRatio > 1) { // Wider than tall
            drawWidth = tileSize;
            drawHeight = (int)(tileSize / aspectRatio);
        } else { // Taller than wide or square
            drawHeight = tileSize;
            drawWidth = (int)(tileSize * aspectRatio);
        }

        // Center the image in the tile
        int xOffset = (tileSize - drawWidth) / 2;
        int yOffset = (tileSize - drawHeight) / 2;

        // Draw the resized image
        g.drawImage(image, x + xOffset, y + yOffset, drawWidth, drawHeight, this);

    }

    // Check if position is a snake
    private boolean isSnake(int position) {
        for (int snake: snakePositions) {
            if (snake == position) return true;
        }
        return false;
    }

    // Check if position is a tunnel
    private boolean istunnel(int position) {
        for (int tunnel: tunnelPositions) {
            if (tunnel == position) return true;
        }
        return false;
    }

    public static int calculate(int position, int add) {
        position += add;
        if (position > 100) position = 100;
        return position;
    }

    // Helper method to generate dynamic messages
    public static String getMineMessage(int from, int to) {
        if (from == 95) return "Big mine found! Sliding all the way down to " + to;
        return (from % 2 == 0 ? "Oh no! Mine found! " : "Hidden mine appeared! ") + "Sliding down to " + to;
    }

    public static void main(String[] args) {
        JPanel f = new App();
        Random ran = new Random();
        JFrame frame = new JFrame("A game of Mines and Tunnels");
        frame.add(f);
        frame.setSize(1920, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel board = new App();

        JPanel textPanel = new JPanel();
        textPanel.setPreferredSize(new Dimension(500, 800));
        textPanel.setLayout(new BorderLayout());
        textPanel.setFont(new Font("Arial", Font.BOLD, 40));

        JTextArea textBox = new JTextArea();
        textBox.setText("Game Info:\nMines and tunnelss, whoever reaches 100 first wins between the user and computer. \n" +
            "If you hit a mine you will answer a trivia question, if you get it right you dont go down\n" +
            "If you get it wrong you go down\n\n" +
            "Colors may be misleading. \n" +
            "Beware that they may not always reflect the space and its properties.\n");
        textBox.setEditable(false);
        textBox.setLineWrap(true);
        textBox.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textBox);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        textPanel.add(scrollPane, BorderLayout.CENTER);

        textPanel.add(new JScrollPane(textBox), BorderLayout.CENTER);

        frame.add(board, BorderLayout.CENTER);
        frame.add(textPanel, BorderLayout.EAST);
        frame.setVisible(true);

        // Game loop
        while (userTemp < 100 && compUser < 100) { //while both the computer and the player are on tiles lower than 100
            if (userTemp == 100) {
                textBox.append("\n\nUser wins!");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                frame.dispose(); // This closes the window
                System.exit(0);
                break;
            } else if (compUser == 100) {
                textBox.append("\n\nComputer wins, better luck next time.");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                frame.dispose(); // This closes the window
                System.exit(0);
                break;
            }
            int roll;
            try {
                if (userTemp != 100 && compUser != 100)
                    JOptionPane.showMessageDialog(null, "Press enter  or 'ok' to roll.");
                roll = ran.nextInt(1, 7); // Player's roll
                JOptionPane.showMessageDialog(null, "You rolled a " + roll + "!");
                int oldPosition = userTemp;

                userTemp = calculate(userTemp, roll);
                textBox.append("\nUser Turn: Rolled " + roll + ". Moved from " + oldPosition + " to " + userTemp);
                textBox.setCaretPosition(textBox.getDocument().getLength());

                // Check if player hit a ladder
                Map<Integer, Integer> tunnels = Map.of(4, 14, 9, 31, 21, 42, 28, 84, 51, 67, 72, 91, 80, 99);
                if (tunnels.containsKey(userTemp)) {
                    int newPos = tunnels.get(userTemp);
                    textBox.append("\nYou found a tunnel at " + userTemp + "! Digging up to " + newPos);
                    userTemp = newPos;
                }
                textBox.setCaretPosition(textBox.getDocument().getLength());

                Map<Integer, Integer> mines = Map.of(32, 10, 50, 30, 65, 50, 75, 50, 95, 5, 34, 6, 30, 6);

                if (mines.containsKey(userTemp) && oldPosition < userTemp) {
                    int newPos = mines.get(userTemp);
                    textBox.append("\n" + getMineMessage(userTemp, newPos));
                    textBox.setCaretPosition(textBox.getDocument().getLength());
                    if (!askRiddle()) userTemp = newPos;
                }
                frame.repaint();
                Thread.sleep(1000);
                if (userTemp != 100 && compUser != 100)
                    JOptionPane.showMessageDialog(null, "Computer is rolling...");
                Thread.sleep(900);
                roll = ran.nextInt(1, 7); // Computer's roll
                JOptionPane.showMessageDialog(null, "Computer rolled a " + roll + "!");
                oldPosition = compUser;
                compUser = calculate(compUser, roll);
                textBox.append("\nComputer Turn: Rolled " + roll + ". Moved from " + oldPosition + " to " + compUser);
                frame.repaint();
                Thread.sleep(1000);
                //System.out.print(compUser + " ");
                if (userTemp == 100) {
                    textBox.append("\n\nUser wins!");
                    JOptionPane.showMessageDialog(null, "\nUser wins!");
                    break;
                } else if (compUser == 100) {
                    textBox.append("\n\nComputer wins, better luck next time.");
                    JOptionPane.showMessageDialog(null, "\nComputer wins, better luck next time.");
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}