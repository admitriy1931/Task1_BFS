package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

enum State
{
    Empty,
    Wall,
    Visited
}
public class Main {
    public static void main(String[] args) {

        var data = args[0];
        var result = args[1];
        List<String> lines = new ArrayList();
        try (BufferedReader reader = new BufferedReader(new FileReader(data))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        String[] linesAsArrayWithSpace = lines.toArray(new String[lines.size()]);
        String[] linesAsArray = new String[lines.size()];

        for (int i = 0; i < lines.size(); i++) {
            linesAsArray[i] = linesAsArrayWithSpace[i].replaceAll("\\s+", "");
        }
        var countOfStr = Integer.parseInt(linesAsArray[0]);
        var countOfCol = Integer.parseInt(linesAsArray[1]);
        State map[][] = new State[countOfStr][countOfCol];

        for (int x = 0; x < countOfStr; x++)
            for (int y = 0; y < countOfCol; y++) {
                if (linesAsArray[x + 2].charAt(y) == '1') {
                    map[x][y] = State.Wall;
                } else map[x][y] = State.Empty;
            }

        Point startPoint = new Point(Integer.parseInt(linesAsArrayWithSpace[2 + Integer.parseInt(linesAsArray[0])].split(" ")[0]) - 1,
                Integer.parseInt(linesAsArrayWithSpace[2 + Integer.parseInt(linesAsArray[0])].split(" ")[1]) - 1);
        Point endPoint = new Point(Integer.parseInt(linesAsArrayWithSpace[3 + Integer.parseInt(linesAsArray[0])].split(" ")[0]) - 1,
                Integer.parseInt(linesAsArrayWithSpace[3 + Integer.parseInt(linesAsArray[0])].split(" ")[1]) - 1);
        var track = getTrack(map,startPoint,endPoint,countOfCol,countOfStr);

        var pathIt = endPoint;
        var res = new ArrayList<Point>();
        while (pathIt != null) {
            res.add(pathIt);
            pathIt = track.get(pathIt);
        }

        try(FileWriter writer = new FileWriter(result))
        {
            if (track.containsKey(res.get(0)) == false){
                writer.append("N");
            }
            else{
                writer.append("Y" + "\n");
                for(int k = res.size() - 1; k >=0; k--){
                    int a = res.get(k).x + 1;
                    int b = res.get(k).y + 1;
                    String c = a + " " + b;
                    writer.append(c + "\n");
                }
            }
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public static Map<Point, Point> getTrack(State map[][], Point startPoint, Point endPoint, int countOfCol, int countOfStr) {
        Map<Point, Point> track = new HashMap();
        var queue = new MyQueue(countOfCol * countOfStr);
        track.put(startPoint, null);
        queue.insert(startPoint);
        while (queue.isEmpty() == false) {
            var point = queue.remove();
            if (point.x < 0 || point.x >= map.length || point.y < 0 || point.y >= map[0].length) continue;
            if (map[point.x][point.y] == State.Visited || map[point.x][point.y] == State.Wall) continue;
            map[point.x][point.y] = State.Visited;
            for (var dy = -1; dy <= 1; dy++) {
                for (var dx = -1; dx <= 1; dx++) {
                    if ((dx != 0 && dy != 0)) continue;
                    if (track.containsKey(new Point(point.x + dx, point.y + dy))) continue;
                    track.put(new Point(point.x + dx, point.y + dy), point);
                    queue.insert(new Point(point.x + dx, point.y + dy));
                }
            }
            if (track.containsKey(endPoint)) break;
        }
        return track;
    }
}