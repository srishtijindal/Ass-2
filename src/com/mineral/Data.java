package com.mineral;

import java.util.ArrayList;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Data {

  public int playersCount = 3;
  public int activePlayersCount = 3;
  public ArrayList<Player> players;
  public ArrayList<Pane> playerPanes;
  public ArrayList<Card> cards;
  public Pane dealer;
  public VBox rightVBox;
  public VBox leftVBox;
  public HBox userCards;
  public boolean pass;
  public Category category;
  public Text winersText;

  public Pane dealerPane;
    public Pane firstDealerCard;
    public Pane middleDealerCard;
    public Pane lastDealerCard;
    public BorderPane dealerBorder;
    public Text dealerTxt;
    public Text dealerCardCountTxt;
    public Pane activeCard;
}
