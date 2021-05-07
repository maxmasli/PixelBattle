import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {

    public void parse(String file, SpritesEnum type, Main main){
        int height = 0;
        int width = 0; 

        ArrayList<String> list = new ArrayList<String>();

        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String color_1 = reader.readLine();
            String color_2 = reader.readLine();
            String currentLine = reader.readLine();
            width = currentLine.split(" ").length;
            list.add(currentLine);
            
            while (currentLine != null) {
                currentLine = reader.readLine();
                height++;
                if (currentLine != null) {
                    list.add(currentLine);
                }
            }
            reader.close();
            int[][] result = new int[height][width];
        int curHeight = 0;
        for (String line : list) {
            String[] width_line = line.split(" ");
            for (int i = 0; i < width_line.length; i++) {
                result[curHeight][i] = Integer.parseInt(width_line[i]);
            }
            curHeight++;
        }
        switch (type) {
            case HUMAN:
                Pictures.man = result;
                try{
                    main.human1 = Color.decode(color_1);
                    main.human2 = Color.decode(color_2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case TREE:
                Pictures.tree = result;
                try{
                    main.tree1 = Color.decode(color_1);
                    main.tree2 = Color.decode(color_2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case STONE:
                Pictures.stone = result;
                try{
                    main.stone1 = Color.decode(color_1);
                    main.stone2 = Color.decode(color_2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case CASTLE:
                Pictures.castle = result;
                // потом
                break;       
        }
        
        }catch (IOException e){
            System.out.println("файл не найден наверное");
        }
        
    }

}