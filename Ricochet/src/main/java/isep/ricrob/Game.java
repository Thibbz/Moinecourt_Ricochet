package isep.ricrob;

import isep.jfx.Time;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static isep.ricrob.Game.Status.*;
import static isep.ricrob.Token.Color.*;


public class Game {


    // * Instance globale de gestion du jeu

    public static Game context;

    // Taille du plateau (SIZE x SIZE)
    public static final int SIZE = 16;

    //Les plateaux partiels avec chaque case et les obstacles correspondant
    public int[][][] partialBoardHard1 = new int[][][]{{{1, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 1, 0, 1, 0}, {1, 1, 0, 0, 0}, {1, 1, 1, 1, 0}},
            {{1, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 1, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 1, 0, 0}},
            {{1, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 1, 1, 0, 0}, {1, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}},
            {{1, 0, 0, 1, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}},
            {{1, 0, 1, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 1, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}},
            {{1, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 1, 0, 0, 0}, {1, 0, 1, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}},
            {{1, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 1, 0, 0, 0}, {1, 0, 0, 1, 0}, {0, 0, 0, 0, 0}},
            {{1, 0, 0, 1, 0}, {0, 0, 0, 1, 0}, {0, 0, 0, 1, 0}, {0, 0, 0, 1, 0}, {0, 1, 0, 1, 0}, {1, 0, 0, 1, 0}, {0, 0, 1, 1, 0}, {0, 0, 0, 1, 0}}};

    public int[][][] partialBoardHard2 = new int[][][]{{{1, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 1, 0, 0, 0}, {1, 1, 1, 1, 0}},
            {{1, 0, 0, 1, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 1, 0, 0}},
            {{1, 0, 1, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 1, 0, 1, 0}, {1, 0, 0, 0, 0}, {0, 0, 0, 1, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}},
            {{1, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 0, 0, 0}, {0, 1, 1, 0, 0}, {1, 0, 0, 0, 0}, {0, 0, 0, 0, 0}},
            {{1, 0, 0, 0, 0}, {0, 1, 0, 0, 0}, {1, 0, 0, 1, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 1, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}},
            {{1, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 1, 0, 0}, {0, 1, 0, 0, 0}, {1, 0, 1, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}},
            {{1, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}},
            {{1, 0, 0, 1, 0}, {0, 0, 0, 1, 0}, {0, 0, 0, 1, 0}, {0, 1, 0, 1, 0}, {1, 0, 0, 1, 0}, {0, 0, 0, 1, 0}, {0, 0, 0, 1, 0}, {0, 0, 0, 1, 0}}};

    public int[][][] partialBoardHard3 = new int[][][]{{{1, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 1, 0}, {0, 0, 0, 0, 0}, {0, 1, 0, 0, 0}, {1, 1, 1, 1, 0}},
            {{1, 0, 0, 1, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 1, 1, 0, 0}, {1, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 1, 0, 0}},
            {{1, 0, 1, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}},
            {{1, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}},
            {{1, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 1, 0, 1, 0}, {1, 0, 0, 0, 0}},
            {{1, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 1, 0, 0, 0}, {1, 0, 1, 0, 0}, {0, 0, 0, 0, 0}},
            {{1, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 1, 0, 0, 0}, {1, 0, 0, 1, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}},
            {{1, 0, 0, 1, 0}, {0, 0, 0, 1, 0}, {0, 0, 0, 1, 0}, {0, 0, 1, 1, 0}, {0, 0, 0, 1, 0}, {0, 1, 0, 1, 0}, {1, 0, 0, 1, 0}, {0, 0, 0, 1, 0}}};

    public int[][][] partialBoardHard4 = new int[][][]{{{1, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 1, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 1, 0}, {0, 0, 0, 0, 0}, {0, 1, 0, 0, 0}, {1, 1, 1, 1, 0}},
            {{1, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 1, 1, 0, 0}, {1, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 1, 0, 0}},
            {{1, 0, 0, 1, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 1, 0, 0, 0}, {1, 0, 0, 1, 0}, {0, 0, 0, 0, 0}},
            {{1, 0, 1, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 0, 0, 0}},
            {{1, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 1, 0, 1, 0}, {1, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}},
            {{1, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 1, 0, 0, 0}, {1, 0, 1, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}},
            {{1, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}},
            {{1, 0, 0, 1, 0}, {0, 0, 0, 1, 0}, {0, 1, 0, 1, 0}, {1, 0, 0, 1, 0}, {0, 0, 0, 1, 0}, {0, 0, 0, 1, 0}, {0, 0, 0, 1, 0}, {0, 0, 0, 1, 0}}};

    //Plateau de chaque case  avec la classe Tile
    public Tile[][] partialBoard1 = new Tile[SIZE / 2][SIZE / 2];
    public Tile[][] partialBoard2 = new Tile[SIZE / 2][SIZE / 2];
    public Tile[][] partialBoard3 = new Tile[SIZE / 2][SIZE / 2];
    public Tile[][] partialBoard4 = new Tile[SIZE / 2][SIZE / 2];

    //Permet de commencer la partie
    public static void start() {
        if (Game.context != null) {
            throw new RuntimeException
                    ("Impossible de lancer plusieurs fois la partie...");
        }
        Game.context = new Game();
        Game.context.setStatus(CHOOSE_PLAYER);
    }

    // * ---


    // Constructeur privé (instance unique créée par "start()" )
    private Game() {
        board = new Tile[SIZE][SIZE];

        generatePartialBoard();// Permet de transformer les tableau int[][][] en tableau Tile[][]
        generateBoardWithPartialBoard();//Generer le plateau entier avec des plateaux partiels

        robots = new HashMap<>();
        robots.put(RED, new Token(RED));
        robots.put(GREEN, new Token(GREEN));
        robots.put(BLUE, new Token(BLUE));
        robots.put(YELLOW, new Token(YELLOW));

        for (Map.Entry<Token.Color, Token> entry : robots.entrySet()) {//détéction de la position initiale des robotspour créer un obstacle
            this.board[entry.getValue().getLig()][entry.getValue().getCol()].setMid(1);
        }

        Token.Color[] colors = Token.Color.values();//Création de la cible avec une couleur aléatoire
        int randomColorIndex = (new Random()).nextInt(colors.length);
        target = new Token(colors[randomColorIndex]);
    }

    //Affichage du plateaux dans la console pour le debug
    private void displayBoard(Tile[][] boardDis) {
        for (int i = 0; i < boardDis.length; i++) {
            for (int j = 0; j < boardDis.length; j++) {
                System.out.print(boardDis[i][j].toString());
                System.out.print(" ");
            }
            System.out.print("\n");
        }
    }


    private void generatePartialBoard() {
        for (int i = 0; i < SIZE / 2; i++) {
            for (int j = 0; j < SIZE / 2; j++) {
                partialBoard1[i][j] = new Tile(partialBoardHard1[i][j][0], partialBoardHard1[i][j][1], partialBoardHard1[i][j][2], partialBoardHard1[i][j][3], partialBoardHard1[i][j][4]);
                partialBoard2[i][j] = new Tile(partialBoardHard2[i][j][0], partialBoardHard2[i][j][1], partialBoardHard2[i][j][2], partialBoardHard2[i][j][3], partialBoardHard2[i][j][4]);
                partialBoard3[i][j] = new Tile(partialBoardHard3[i][j][0], partialBoardHard3[i][j][1], partialBoardHard3[i][j][2], partialBoardHard3[i][j][3], partialBoardHard3[i][j][4]);
                partialBoard4[i][j] = new Tile(partialBoardHard4[i][j][0], partialBoardHard4[i][j][1], partialBoardHard4[i][j][2], partialBoardHard4[i][j][3], partialBoardHard4[i][j][4]);
            }
        }
    }

    private Tile[][] rotatePartialBoard90(Tile[][] partialBoard) {

        for (int i = 0; i < SIZE / 2; i++) {
            for (int j = 0; j < SIZE / 2; j++) {
                Tile newTile = new Tile(0, 0, 0, 0, 0);
                if (partialBoard[i][j].getDown() == 1) {
                    newTile.setLeft(1);
                }
                if (partialBoard[i][j].getLeft() == 1) {
                    newTile.setUp(1);
                }
                if (partialBoard[i][j].getUp() == 1) {
                    newTile.setRight(1);
                }
                if (partialBoard[i][j].getRight() == 1) {
                    newTile.setDown(1);
                }
                partialBoard[i][j] = newTile;
            }
        }

        Tile[][] newPartialBoard = new Tile[8][8];
        for (int i = 0; i < SIZE / 2; i++) {
            for (int j = 0; j < SIZE / 2; j++) {
                newPartialBoard[i][SIZE / 2 - 1 - j] = new Tile(0, 0, 0, 0, 0);
                newPartialBoard[i][SIZE / 2 - 1 - j] = partialBoard[j][i];
            }
        }

        return newPartialBoard;
    }

    private void generateBoardWithPartialBoard() {
        partialBoard4 = rotatePartialBoard90(partialBoard4);
        partialBoard3 = rotatePartialBoard90(rotatePartialBoard90(partialBoard3));
        partialBoard2 = rotatePartialBoard90(rotatePartialBoard90(rotatePartialBoard90(partialBoard2)));

        for (int i = 0; i < SIZE / 2; i++) {//Création du coin haut gauche
            for (int j = 0; j < SIZE / 2; j++) {
                board[i][j] = new Tile(partialBoard4[i][j]);
            }
        }
        for (int i = 0; i < SIZE / 2; i++) {//Création du coin haut droite
            for (int j = 0; j < SIZE / 2; j++) {
                board[i][SIZE / 2 + j] = new Tile(partialBoard3[i][j]);
            }
        }
        for (int i = 0; i < SIZE / 2; i++) {//Création du coin bas droite
            for (int j = 0; j < SIZE / 2; j++) {
                board[SIZE / 2 + i][SIZE / 2 + j] = new Tile(partialBoard2[i][j]);
            }
        }
        for (int i = 0; i < SIZE / 2; i++) {//Création du coin bas gauche
            for (int j = 0; j < SIZE / 2; j++) {
                board[SIZE / 2 + i][j] = new Tile(partialBoard1[i][j]);
            }
        }
    }


    // * Gestion des événements du jeu

    public void processSelectPlayer() {
        if (this.status == CHOOSE_PLAYER) {
            // Action suivante attendue : choisir la case cible
            setStatus(CHOOSE_ROBOT);
        }
    }

    public void processSelectRobot(Token.Color color) {
        if (this.status == CHOOSE_ROBOT) {
            this.selectedRobot = this.robots.get(color);
            // Action suivante attendue : choisir la case cible
            setStatus(CHOOSE_TILE);
        }
    }

    public String processSelectTile(int col, int lig) {
        if (this.status == CHOOSE_TILE) {
            if (
                    (this.selectedRobot.getCol() != col)
                            &&
                            (this.selectedRobot.getLig() != lig)
            ) {
                return "Les déplacements en diagonale sont interdits";
            } else {
                board[this.selectedRobot.getLig()][this.selectedRobot.getCol()].setMid(0);
                int[] newColLig = selectTile(chooseDirection(col, lig));
                col = newColLig[0];
                lig = newColLig[1];
                System.out.println("col : " + col + " ligne : " + lig);
                this.selectedRobot.setPosition(col, lig);
                board[lig][col].setMid(1);

                // Action suivante attendue : choisir un robot
                setStatus(CHOOSE_ROBOT);

                if (!victoryBool) {
                    this.nbCoup += 1;
                }

                setNbCoup();//Change le Label nbCoup
                victory();//Vérifier s'il y a eu victoire
                return "MOVE";
            }
        }
        return null;
    }

    //Vérification des conditions de victoire
    private void victory() {
        if (this.selectedRobot.getCol() == this.target.getCol() && this.selectedRobot.getLig() == this.target.getLig()
                && this.selectedRobot.getColor() == this.target.getColor()) {// Si le pion est à la même position et de la même couleur que la cible
            System.out.println("Vous avez gagne");
            this.victoryBool = true;
            setNbCoup();
        }
    }

    //Dterminer la direction dans laquelle à cliquer l'utilisateur par rapport au pion
    private direction chooseDirection(int col, int lig) {
        if (col < this.selectedRobot.getCol() && lig == this.selectedRobot.getLig()) {
            return direction.left;
        } else if (col > this.selectedRobot.getCol() && lig == this.selectedRobot.getLig()) {
            return direction.right;
        } else if (lig < this.selectedRobot.getLig() && col == this.selectedRobot.getCol()) {
            return direction.up;
        } else if (lig > this.selectedRobot.getLig() && col == this.selectedRobot.getCol()) {
            return direction.down;
        }

        return direction.stay;
    }

    public enum direction {left, right, up, down, stay}

    /*
    Determiner la case où doit s'arrêter le pion

    Paramètre : dir => la direction choisie

    Output : Tableau d'entier des coordonnées de la nouvelle case du pion
     */
    private int[] selectTile(direction dir) {
        int newCol = 0, newLig = 0;
        if (dir == direction.left) {
            newLig = this.selectedRobot.getLig();
            for (int i = this.selectedRobot.getCol(); i > 0; i--) {
                System.out.println(board[i][this.selectedRobot.getLig()]);
                if (board[this.selectedRobot.getLig()][i].getLeft() == 1) {//S'il y a un obstacle
                    newCol = i;
                    break;
                } else if (board[this.selectedRobot.getLig()][i - 1].getMid() == 1) {//S'il y a déja un pion sur la case
                    newCol = i;
                    break;
                }
            }
        } else if (dir == direction.right) {
            newLig = this.selectedRobot.getLig();
            for (int i = this.selectedRobot.getCol(); i < SIZE; i++) {
                if (board[this.selectedRobot.getLig()][i].getRight() == 1) {
                    newCol = i;
                    break;
                } else if (board[this.selectedRobot.getLig()][i + 1].getMid() == 1) {
                    newCol = i;
                    System.out.println("pion adverse détected");
                    break;
                }
            }
        } else if (dir == direction.up) {
            newCol = this.selectedRobot.getCol();
            for (int i = this.selectedRobot.getLig(); i > 0; i--) {
                if (board[i][newCol].getUp() == 1) {
                    newLig = i;
                    break;
                } else if (board[i - 1][newCol].getMid() == 1) {
                    newLig = i;
                    System.out.println("pion adverse détected");
                    break;
                }
            }
        } else if (dir == direction.down) {
            newCol = this.selectedRobot.getCol();
            for (int i = this.selectedRobot.getLig(); i < SIZE; i++) {
                if (board[i][newCol].getDown() == 1) {
                    newLig = i;
                    break;
                } else if (board[i + 1][newCol].getMid() == 1) {
                    newLig = i;
                    System.out.println("pion adverse détected");
                    break;
                }
            }
        } else if (dir == direction.stay) {
            newCol = this.selectedRobot.getCol();
            newLig = this.selectedRobot.getLig();
        }

        return new int[]{newCol, newLig};
    }

    // * ---

    // * Etat courant du jeu

    public enum Status {
        CHOOSE_PLAYER("Cliquez sur le bouton [Jouer]"),
        CHOOSE_ROBOT("Cliquez sur le robot à déplacer"),
        CHOOSE_TILE("Cliquez sur la case destination");

        Status(String toolTip) {
            this.toolTip = toolTip;
        }

        private String toolTip;

        public String getToolTip() {
            return this.toolTip;
        }
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
        // Mise à jour du libellé d'état sur l'affichage
        StringBuilder statusMessage = new StringBuilder();
        if (playerNameProperty.get() != null) {
            statusMessage.append(playerNameProperty.get());
            statusMessage.append(" : ");
        }
        statusMessage.append(status.getToolTip());
        this.statusToolTipProperty.set(statusMessage.toString());
    }

    private Status status;
    // "Binding JFX" - Synchronisation avec "MainController.statusLabel"
    public StringProperty statusToolTipProperty = new SimpleStringProperty();


    public StringProperty nbCoupToolTipProperty = new SimpleStringProperty();
    private int nbCoup = 0;
    private boolean victoryBool = false;

    //Changement du Label nbCoup
    public void setNbCoup() {
        // Mise à jour du libellé nbCoup sur l'affichage
        StringBuilder nbCoupMessage = new StringBuilder();
        if (victoryBool) {
            nbCoupMessage.append("Victoire en ");
            nbCoupMessage.append(nbCoup);
            nbCoupMessage.append(" coups");
        } else {
            if (playerNameProperty.get() != null) {
                nbCoupMessage.append(playerNameProperty.get());
                nbCoupMessage.append(" : ");
            }
            nbCoupMessage.append(nbCoup);
        }
        this.nbCoupToolTipProperty.set(nbCoupMessage.toString());
    }


    // "Binding JFX" - Synchronisation avec "PlayerController.name"
    public StringProperty playerNameProperty = new SimpleStringProperty();

    private Token selectedRobot;

    public Token getSelectedRobot() {
        return this.selectedRobot;
    }

    // * ---

    // Le plateau de SIZE x SIZE cases
    private Tile[][] board;

    // Les 4 robots
    private Map<Token.Color, Token> robots;

    public Map<Token.Color, Token> getRobots() {
        return this.robots;
    }

    // La cible
    private Token target;

    public Token getTarget() {
        return this.target;
    }

    //Le timer
    private Time time = new Time(0);
    public StringProperty timerToolTipProperty = new SimpleStringProperty();

    Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(1),
                    e -> {
                        time.oneSecondPassed();
                        timerToolTipProperty.set(time.getCurrentTime());
                        if(time.getSecond()>30){
                            //Mettre en rouge le timer quand il dépasse 30s
                        }
                    }));

    public Timeline getTimeline() {
        return this.timeline;
    }
}
