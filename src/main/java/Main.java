import file.FileManager;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
//        FileManager.writeFile("test.txt",
//                Arrays.asList(FileManager.writeObjects(new Object[]{1, 2.0, "text"}),
//                        FileManager.writeObjects(new Object[]{"Bla", 12})));
//        FileManager.readFile("test.txt").stream()
//                .map(FileManager::parseLine)
//                .flatMap(Arrays::stream)
//                .forEach(System.out::println);
        // 1
        List<List<Integer>> worldMatrix = FileManager.readFile("level3_example.in").stream()
                .map(FileManager::parseLine)
                .map(line -> Arrays.stream(line).map(num -> Integer.parseInt(num.toString())).collect(Collectors.toList()))
                .collect(Collectors.toList());
        List<Integer> size = worldMatrix.remove(0);

        // 2
        List<List<Cell>> cells = new ArrayList<>();
        List<Integer> countries = new ArrayList<>();
        Integer rows = size.get(0);
        for (int i = 0; i < rows; i++) {
            cells.add(new ArrayList<>());
            Integer colls = size.get(1) * 2;
            for (int j = 0; j < colls; j += 2) {
                Integer altitude = worldMatrix.get(i).get(j);
                Integer country = worldMatrix.get(i).get(j + 1);
                Cell cell = new Cell(altitude, country, i, j / 2);
                cells.get(i).add(cell);
                if (!countries.contains(country)) {
                    countries.add(country);
                }
                if (i - 1 >= 0) {
                    cell.addNeighbour(worldMatrix.get(i - 1).get(j + 1));
                } else {
                    cell.addNeighbour(-1);
                }
                if (i + 1 < rows) {
                    cell.addNeighbour(worldMatrix.get(i + 1).get(j + 1));
                } else {
                    cell.addNeighbour(-1);
                }
                if (j - 1 >= 0) {
                    cell.addNeighbour(worldMatrix.get(i).get(j - 1));
                } else {
                    cell.addNeighbour(-1);
                }
                if (j + 2 < colls) {
                    cell.addNeighbour(worldMatrix.get(i).get(j + 3));
                } else {
                    cell.addNeighbour(-1);
                }
            }
        }
        Map<Integer, List<Cell>> collect = cells.stream()
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(Cell::getCountry));

        Map<Integer, Cell> centers = new HashMap<>();
        for (int i = 0; i < collect.size(); i++) {
            Integer minX = collect.get(i).get(0).x;
            Integer minY = collect.get(i).get(0).y;
            Integer maxX = collect.get(i).get(0).x;
            Integer maxY = collect.get(i).get(0).y;
            for (int j = 0; j < collect.get(0).size(); j++) {
                Cell cell = collect.get(i).get(j);
                minX = cell.x < minX ? cell.x : minX;
                minY = cell.y < minY ? cell.y : minY;
                maxX = cell.x > maxX ? cell.x : maxX;
                maxY = cell.y > maxY ? cell.y : maxY;
            }
            centers.put(i, cells.get((minX.x + maxX.x) / 2).get((minY.y + maxY.y) / 2));
        }
        HashMap<Integer, Cell> result = new HashMap<>();

        for (Map.Entry<Integer, Cell> center : centers.entrySet()) {
            Optional<Cell> first = cells.stream().flatMap(List::stream).filter(c -> areSameCords(center.getValue(), c)).findFirst();
            if (first.isPresent() && first.get().country.equals(center.getValue().getCountry())) {
                result.put(center.getKey(), first.get());
            } else {

                Integer left = checkLeft(cells, center);
                Integer min = left;
                Integer up = checkUp(cells, center);
                min = up < min ? up : min;
                Integer down = checkDown(cells, center);
                down = down < min ? down : min;
                Integer right = checkRight(cells, center);
                right = right < min ? right : min;
                if(left == min){
                    result.put(center.getKey(), cells.get(center.getValue().x - left).get(center.getValue().y));
                }
                if(up == min){
                    result.put(center.getKey(), cells.get(center.getValue().x).get(center.getValue().y - up));
                }
                if(down == min){
                    result.put(center.getKey(), cells.get(center.getValue().x + down).get(center.getValue().y));
                }
                if(right == min){
                    result.put(center.getKey(), cells.get(center.getValue().x).get(center.getValue().y + right));
                }
            }
        }

        System.out.println("test");
    }

    private static Integer checkLeft(List<List<Cell>> cells, Map.Entry<Integer, Cell> center) {
        int a = 0;
        while (cells.get(center.getValue().x - a).get(center.getValue().y).x > 0) {
            if (cells.get(center.getValue().x - a).get(center.getValue().y).country.equals(center.getValue().getCountry())) {
                return a;
            }
            a++;
        }
        return Integer.MAX_VALUE;
    }
    private static Integer checkUp(List<List<Cell>> cells, Map.Entry<Integer, Cell> center) {
        int a = 0;
        while (cells.get(center.getValue().y - a).get(center.getValue().y).y > 0) {
            if (cells.get(center.getValue().y - a).get(center.getValue().y).country.equals(center.getValue().getCountry())) {
                return a;
            }
            a++;
        }
        return null;
    }
    private static Integer checkDown(List<List<Cell>> cells, Map.Entry<Integer, Cell> center) {
        int a = 0;
        while (cells.get(center.getValue().y + a).get(center.getValue().y).y < cells.size()) {
            if (cells.get(center.getValue().y + a).get(center.getValue().y).country.equals(center.getValue().getCountry())) {
                return a;
            }
            a++;
        }
        return null;
    }
    private static Integer checkRight(List<List<Cell>> cells, Map.Entry<Integer, Cell> center) {
        int a = 0;
        while (cells.get(center.getValue().x + a).get(center.getValue().y).x < cells.get(0).size()) {
            if (cells.get(center.getValue().x + a).get(center.getValue().y).country.equals(center.getValue().getCountry())) {
                return a;
            }
            a++;
        }
        return null;
    }

    public static boolean areSameCords (Cell cell1, Cell cell2) {
        return cell1.x.equals(cell2.x) && cell1.y.equals(cell2.y);
    }

    public static int getMin(List<List<Integer>> matrix) {
        int min = matrix.get(0).get(0);
        List<Object> collect = matrix.stream().flatMap(List::stream).collect(Collectors.toList());
        for (Object a : collect) {
            if ((int) a < min) {
                min = (int) a;
            }
        }
        return min;
    }

    public static int getMax(List<List<Integer>> matrix) {
        int max = matrix.get(0).get(0);
        List<Object> collect = matrix.stream().flatMap(List::stream).collect(Collectors.toList());
        for (Object a : collect) {
            if ((int) a > max) {
                max = (int) a;
            }
        }
        return max;
    }

    public static int getAvg(List<List<Integer>> matrix) {
        long sum = 0;
        List<Integer> collect = matrix.stream().flatMap(List::stream).collect(Collectors.toList());
        for (Integer a : collect) {
            sum += a;
        }
        return (int) (sum / collect.size());
    }
}
