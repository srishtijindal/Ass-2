package com.mineral;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Dealer {


    private static Media media;
    private static MediaPlayer mp;
    public static int gameOverFlag = 0;


    public static void createCards(ArrayList<Card> cards){

        Dealer dealer = new Dealer();
        dealer.parsing(cards);

        int s = cards.size();

        System.out.println("Cards created \n");
        for(int i = 0; i < s; i++){
            System.out.println("card " + i + " - " + cards.get(i).getTitle());
        }

    }

    public static void alert(){
        mp.play();
        mp.setVolume(0.1);
        mp.seek(Duration.ZERO);
    }

    public static void dealCards(Player player, ArrayList<Card> cards) {

        for(int i = 0; i < 8; i++){

            int r = (int) Math.floor(Math.random() * cards.size());
            Card c = cards.get(r);
            player.getCards().add(c);
            cards.remove(r);
            cards.trimToSize();
        }

        System.out.println("player - " + player.getId() + " has received their cards");

    }

  public static Card nextCard(ArrayList<Card> cards) {

        int size = cards.size();

        if(size == 0){
            return null;
        }

        int r = (int) Math.floor(Math.random() * size);
        Card c = cards.get(r);
        cards.remove(r);
        cards.trimToSize();

        System.out.println("dealer give card - " + c.getTitle());
        return c;
    }

    private void parsing(ArrayList<Card> cards){

        ClassLoader cl = this.getClass().getClassLoader();

        //media = new Media((new File("media/"+ "alert.mp3")).toURI().toString());
        media = new Media((new File(cl.getResource("alert.mp3").getFile())).toURI().toString());
        mp = new MediaPlayer(media);

        try {
            File inputFile = new File(cl.getResource("MstCards.xml").getFile());

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            Element array = (Element) doc.getElementsByTagName("array").item(0);
            NodeList cardList = array.getElementsByTagName("dict");
            for(int i = 0; i < 60; i++){

                Card card;

                Element elem = (Element) cardList.item(i);
                NodeList string = elem.getElementsByTagName("string");

                String title;
                String subtitle;
                String chemistry;
                String classification;
                String crystalSystem;
                String[] occurrence;
                String hardness;
                String specificGravity;
                String cleavage;
                String crustalAbundance;
                String economicValue;
                String type;
                String url;

                type = string.item(2).getTextContent();

                title = string.item(3).getTextContent();
                url = string.item(0).getTextContent();

                if(type.equals("play")){

                    chemistry = string.item(4).getTextContent();
                    classification = string.item(5).getTextContent();
                    crystalSystem = string.item(6).getTextContent();
                    hardness = string.item(7).getTextContent();
                    specificGravity = string.item(8).getTextContent();
                    cleavage = string.item(9).getTextContent();
                    crustalAbundance = string.item(10).getTextContent();
                    economicValue = string.item(11).getTextContent();

                    Element n = (Element) elem.getElementsByTagName("array").item(0);
                    NodeList nl = n.getElementsByTagName("item");
                    int c = nl.getLength();

                    occurrence = new String [c];

                    for(int j = 0; j < c; j++){
                        occurrence[j] = nl.item(j).getTextContent();
                    }

                    card = new Card(title, chemistry, classification, crystalSystem,
                            occurrence, hardness, specificGravity, cleavage, crustalAbundance,
                            economicValue, type, url);

                }else{
                    subtitle = string.item(4).getTextContent();
                    card = new Card(title, subtitle, type, url);
                }

                cards.add(card);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
