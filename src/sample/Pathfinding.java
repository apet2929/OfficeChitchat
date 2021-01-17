package sample;

import javafx.scene.Node;

import java.util.*;

public class Pathfinding {

    private Floor floor;
    public Pathfinding(Floor floor){
        this.floor = floor;
    }

    void startFindPath(int startPosX, int startPosY, int targetPosX, int targetPosY){
        boolean success = false;

        Prop startProp = floor.props[startPosX][startPosY];
        Prop targetProp = floor.props[targetPosX][targetPosY];

        if(startProp.isWalkable() && targetProp.isWalkable()) {
            List<Prop> openSet = new ArrayList<>();
            HashSet<Prop> closedSet = new HashSet<>();
            openSet.add(startProp);

            while(openSet.size() > 0){
                Prop prop = openSet.get(0);
                for(int i = 1; i < openSet.size(); i++){
                    if(openSet.get(i).fCost() < prop.fCost() || openSet.get(i).fCost() == prop.fCost()){
                        if(openSet.get(i).hCost < prop.hCost) prop = openSet.get(i);
                    }
                }

                openSet.remove(prop);
                closedSet.add(prop);

                if(prop == targetProp){
                    retracePath(startProp, targetProp);
                    success = true;
                    System.out.println(floor.path.toString());
                    return;

                }

                for(Prop neighbor : floor.getNeighbors(prop)){
                    if(neighbor != null) {
                        if (!neighbor.isWalkable() || closedSet.contains(neighbor)) {
                            continue;
                        }
                    }
                    int newCostToNeighbor = prop.gCost + getDistance(prop, neighbor);
                    if(newCostToNeighbor < neighbor.gCost || !openSet.contains(neighbor)){
                        neighbor.gCost = newCostToNeighbor;
                        neighbor.hCost = getDistance(neighbor, targetProp);
                        neighbor.parent = prop;

                        if(!openSet.contains(neighbor)){
                            openSet.add(neighbor);
                        }
                    }
                }
            }
        }
    }

    void retracePath(Prop startProp, Prop endProp){
        ArrayList<Prop> path = new ArrayList<>();
        Prop currentProp = endProp;
        while(currentProp != startProp){
            path.add(currentProp);
            currentProp = currentProp.parent;
        }
        path = reverseArrayList(path);
        floor.path = path;
    }

    public ArrayList<Prop> reverseArrayList(ArrayList<Prop> alist)
    {
        // Arraylist for storing reversed elements
        ArrayList<Prop> revArrayList = new ArrayList<>();
        for (int i = alist.size() - 1; i >= 0; i--) {

            // Append the elements in reverse order
            revArrayList.add(alist.get(i));
        }

        // Return the reversed arraylist
        return revArrayList;
    }


    int getDistance(Prop propA, Prop propB){
        int dstX = Math.abs(propA.getPosX() - propB.getPosX());
        int dstY = Math.abs(propA.getPosY() - propB.getPosY());

        if(dstX > dstY) return 14 * dstY + 10*(dstX-dstY);
        return 14 * dstX + 10 * (dstY-dstX);
    }
}
