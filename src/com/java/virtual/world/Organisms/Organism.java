package com.java.virtual.world.Organisms;

import com.java.virtual.world.Coordinates;
import com.java.virtual.world.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Vector;

abstract public class Organism {
    protected int lifetime=0;
    private final int power;
    private final int initiative;
    protected Coordinates coordinates;
    private final Color color;
    private final String sign;
    protected World world;
    private int killed=0;


    public Organism(World world,int initiative,int power, Coordinates coordinates, Color color, String sign){
        this.power=power;
        this.initiative=initiative;
        this.coordinates=coordinates;
        this.world=world;
        this.color=color;
        this.sign=sign;
        world.getWorldBoard()[coordinates.GetY()][coordinates.GetX()] = this;
        world.getOrganismsArray().add(this);
    }
    abstract public void Action();
    abstract public boolean Collision(Organism other);

    public void setLifetime(int lifetime){
        this.lifetime = lifetime;
    }
    public void setCoordinates(Coordinates newCoords){
        Coordinates oldCoords = this.coordinates;
        Organism[][] temp = world.getWorldBoard();
        temp[oldCoords.GetY()][oldCoords.GetX()]=null;
        this.coordinates=newCoords;
        temp[newCoords.GetY()][newCoords.GetX()]=this;
        world.setWorldBoard(temp);


    }

    public int getLifetime(){
        return lifetime;
    }

    public int getPower(){
        return power;
    }
    public int getInitiative(){
        return initiative;
    }
    public int getKilled(){
        return killed;
    }
    public void setKilled(){
        this.killed=1;
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Color getColor() {
        return color;
    }

    public String getSign() {
        return sign;
    }

    public Coordinates GenerateNewCoordinates(){
        Vector<Coordinates> coordinates = new Vector<>();
        int x1 = this.coordinates.GetX()-1;
        int x2 = this.coordinates.GetX() +1;
        int y1 = this.coordinates.GetY()-1;
        int y2 = this.coordinates.GetY()+1;
        if (x1 < 0)
            x1 = 0;
        if (y1 < 0)
            y1 = 0;
        if (x2 == this.world.getWorldX())
            x2-=1;
        if (y2 == this.world.getWorldY())
            y2-=1;
        for(int i=y1;i<=y2;i++)
            for(int j=x1;j<=x2;j++) {
                if(this.coordinates.GetX() == j && this.coordinates.GetY() == i)
                    continue;
                else{
                    System.out.println("CUR "+this.coordinates.GetX() + " "+this.coordinates.GetY());
                    System.out.println("NEW "+j+ " "+i);
                    coordinates.addElement(new Coordinates(j, i));
                    System.out.println(j +" "+i);
                }
            }

        Random seed = new Random();

        return  coordinates.get(seed.nextInt(coordinates.size()));
    }

    public int GetIndex()
    {
        ArrayList<Organism> organisms = world.getOrganismsArray();
        for (int i = 0; i < organisms.size(); i++)
            if (this.coordinates == organisms.get(i).coordinates)
                return i;
        return -1;
    }
}


