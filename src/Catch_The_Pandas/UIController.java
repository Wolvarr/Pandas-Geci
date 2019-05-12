package Catch_The_Pandas;

import Catch_The_Pandas.IO.*;
import Catch_The_Pandas.Resources.GameElements.Floor;
import Catch_The_Pandas.Resources.GameElements.Tile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;

import javax.swing.text.html.ObjectView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UIController {


    Integer radius = 35;
    //stores the tiles mapped to 2d points on the plane of the canvas
    private Map<OnTileObjectView, Tile> objectNodes = new HashMap<>();
    private Map<TileView, Tile> tileNodes = new HashMap<>();

    //contains the floor and the scores for each orangutan
    private Game game;

    //represents if the current click is the first one
    //moves are represented as 2 clicks, the first chooses the orangutan to move
    //the second chooses the tile where the user chooses to move to
    private boolean firstclick = true;
    private Integer orangutanOnTurn = 0;

    //represents the command currently being assembled
    Command currentCommand = null;

    public UIController() throws FileNotFoundException {
    }

    @FXML
    void initialize()  {



        ImageContainer testic = new ImageContainer();
        String basePath = null;
        File currentDir = new File (".");
        try {
            basePath = currentDir.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("This is the prototype main class");
        Catch_The_Pandas.IO.Map testmap = new Catch_The_Pandas.IO.Map(basePath + "/testmap0/");
        Floor testfloor = testmap.build();
        try{
            System.out.println("Size of objectGraphics: " + testmap.objectGraphics.size() + "  Size of testfloor tiles: " + testfloor.getAllTiles().size());
            for(int i = 0; i< testmap.tileGraphics.size(); i++){
            tileNodes.put( testmap.tileGraphics.get(i) , testfloor.getTile(i));

        }} catch (Exception e){
            e.printStackTrace();
        }



        game = new Game(testfloor);

        //testing purposes, pls remove before release
        mainGameCanvas.setOnMouseMoved(event -> {
            //System.out.println(event.getX() + "   " + event.getY());
        });

        mainGameCanvas.setOnMouseClicked(event -> {
            //logic to detect on what object did the user click on
            //getting the location of the mouse when the user clicked
            Point2D clickedPoint = new Point2D(event.getX(), event.getY());

            //starting from a large enough distance that every distance is going to be closer
            //(the window is definitely not 9999 pixels across)
            double smallestDistance = 9999;
            Tile selectedTile = null;
            TileView selectedTileView = null;
            //finds the closest point resembling a node

            for (TileView p : tileNodes.keySet()) {
                //if the distance is the closest yet, we choose the corresponding tile as the closest
                if(p.location == null)
                    System.out.println("object Location is null");
                if (clickedPoint.distance(p.location) < radius) {
                    selectedTile = tileNodes.get(p);
                    selectedTileView = p;
                    System.out.println("BINGO");
                    break;
                }
            }





            if (game.floor.getOrangutan(orangutanOnTurn) != null && selectedTile != null) {
                currentCommand = new Command(CommandType.move, game.floor);
                currentCommand.setTarget(selectedTile);
                currentCommand.setOrangutan(game.floor.getOrangutan(orangutanOnTurn));
                if(currentCommand.execute()){
                    System.out.println("orangutan should move pls move you fat monkey");
                    TileView oldTileView;
                    for (Map.Entry<TileView, Tile> entry : tileNodes.entrySet()) {
                        if (entry.getValue().equals(game.floor.getOrangutan(orangutanOnTurn).getLocation())) {
                            if (entry.getKey().objectView == null)
                                ///EZ MIÉRT NULL GECI
                                System.out.println("shits null, shit hit the fan, shit should not be null");
                                ///FERI EZ MI A RÁK
                            selectedTileView.objectView = entry.getKey().objectView;
                            entry.getKey().objectView = null;
                        }
                    }

                }
                currentCommand = null;

            }
            try {
                drawSomeShit(new ActionEvent());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

    }

    //the canvas where the main game area is being drawn
    @FXML
    public Canvas mainGameCanvas;

    //the exit menu from the top left corner
    @FXML
    public MenuItem exitMenuItem;

    //the list containing the orangutans shown on the left
    //the orangutan currently making a move is highlited
    @FXML
    public ListView orangutanList;


    @FXML
    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }


    //testing purposes, pls remove before release
    @FXML
    public void drawSomeShit(ActionEvent actionEvent) throws FileNotFoundException {
        mainGameCanvas.getGraphicsContext2D().clearRect(0, 0, 2000, 2000);;
        for(TileView p : tileNodes.keySet()) {
            //mainGameCanvas.getGraphicsContext2D().strokeOval(p.location.getX()-35, p.location.getY()-35,70,70);
            mainGameCanvas.getGraphicsContext2D().drawImage(p.images.get(TileState.ok), p.location.getX() - radius, p.location.getY() - radius, 2*radius, 2*radius);
            if(p.objectView != null)
            mainGameCanvas.getGraphicsContext2D().drawImage(p.objectView.images.get(Colour.none), p.location.getX() - radius, p.location.getY()-radius, 2*radius, 2*radius);
            else System.out.println("objectview null");
        }
    }



}
