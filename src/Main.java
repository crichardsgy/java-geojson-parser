import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;

public class Main {
  public static void main(String[] args) 
	{
    clrScr();
    Scanner scan = new Scanner(System.in);
    System.out.print("Please Provide The Path To A GeoJSON\n>> ");
    String jsonpath = scan.next();
		ReadJSON geojson = new ReadJSON(jsonpath);
    ArrayList<IndividualFeature> features = geojson.getIndividualFeatures();
    Integer loopforever = 1;

    while (loopforever == 1)
    {
      clrScr();
      System.out.print("\n");
      System.out.print("[0]Print Entire Feature Collection\n[1]Find All Features Via Geometry Type\n[2]Find All Features Via Property Name\n[3]Find All Features Via Given Point\n[4]Find All Line Strings Via Given Property Name And Value And Convert To Polygon\n[5]Find Area Of All Polygons\n[6]Find Length Of All Line Strings\n[7]Exit Program\n\n>> ");

      String switchval = scan.next();

      switch (switchval) 
      {
        case "0": //prints all
          for (int i = 0; i < features.size(); ++i)
          {
            System.out.println("\n");
            System.out.println("Properties: " + features.get(i).getVals());
            System.out.println("Geometry Type: " + features.get(i).getType());
            System.out.println("Coordinates: " + features.get(i).getCoordinates());
          }
          break;

        case "1": //finds features that have a certain geometry type
          System.out.print("Enter Geometry Type (i.e Point, LineString, Polygon)\n>> ");
          String geotype = scan.next();
          System.out.println("Features Of The " + geotype + " Type Are:");
          //loops through each feature 
          for (int i = 0; i < features.size(); ++i)
          {
            //checks if the type is equal to the user specified type
            if ((features.get(i).getType()).toLowerCase().equals(geotype.toLowerCase()))
            {
              System.out.println("\n");
              System.out.println("Properties: " + features.get(i).getVals());
              System.out.println("Geometry Type: " + features.get(i).getType());
              System.out.println("Coordinates: " + features.get(i).getCoordinates());
            }
          }
          break;

        case "2":  //finds feature that have a certain property key
          System.out.print("Enter Property Name\n>> ");
          String propname = scan.next();
          System.out.println("Features That Have The " + propname + " Key Are:");

          for (int i = 0; i < features.size(); ++i) //loops through each feature
          {
            for (int j = 0; j < features.get(i).getKeys().length(); ++j) //loops through the keys array of that feature
            {
              if ((features.get(i).getKeys().get(j)).equals(propname)) //compares the key to the user specified key
              {
                System.out.println("\n");
                System.out.println("Property Keys: " + features.get(i).getKeys());
                System.out.println("Properties: " + features.get(i).getVals());
                System.out.println("Geometry Type: " + features.get(i).getType());
                System.out.println("Coordinates: " + features.get(i).getCoordinates());
              }
            }
          }
          break;

        case "3": //finds features that have a certain point
          System.out.print("Enter X Value\n>> ");
          String xval = scan.next();
          System.out.print("Enter Y Value\n>> ");
          String yval = scan.next();
          System.out.println("Features With The Point (" + xval + "," + yval + ") Are:");
          for (int i = 0; i < features.size(); ++i) //loops through each feature
          {
            if ((features.get(i).getType()).equals("Point")) //checks if the feature is a point
            {
              if(((features.get(i).getCoordinates().get(0)).toString()).contains(xval) && ((features.get(i).getCoordinates().get(1))).toString().contains(yval)) //compares x and y values to a the 0th and 1st indexes of the lowest level of the coordinates array
              {
                System.out.println("\n");
                System.out.println("Properties: " + features.get(i).getVals());
                System.out.println("Geometry Type: " + features.get(i).getType());
                System.out.println("Coordinates: " + features.get(i).getCoordinates());
              }
            }
            else if ((features.get(i).getType()).equals("LineString")) //checks if the feature is a linestring
            {
              for (int j = 0; j < features.get(i).getCoordinates().length(); ++j) //loops through each point making up the linestring
              {
                if(features.get(i).getCoordinates().getJSONArray(j).get(0).toString().contains(xval) && features.get(i).getCoordinates().getJSONArray(j).get(1).toString().contains(yval)) //compares x and y values to a the 0th and 1st indexes of the lowest level of the coordinates array
                {
                  System.out.println("\n");
                  System.out.println("Properties: " + features.get(i).getVals());
                  System.out.println("Geometry Type: " + features.get(i).getType());
                  System.out.println("Coordinates: " + features.get(i).getCoordinates());
                }
              }
            }
            else //checks if the feature is a polygon (or anything else)
            {
              for (int j = 0; j < features.get(i).getCoordinates().length(); j++) //loops through each polygon
              {
                for (int k = 0; k < features.get(i).getCoordinates().getJSONArray(j).length(); ++k) //loops through each point that makes up a polygon
                {
                  if(features.get(i).getCoordinates().getJSONArray(j).getJSONArray(k).get(0).toString().contains(xval) && features.get(i).getCoordinates().getJSONArray(j).getJSONArray(k).get(1).toString().contains(yval)) //compares x and y values to a the 0th and 1st indexes of the lowest level of the coordinates array
                  {
                    System.out.println("\n");
                    System.out.println("Properties: " + features.get(i).getVals());
                    System.out.println("Geometry Type: " + features.get(i).getType());
                    System.out.println("Coordinates: " + features.get(i).getCoordinates());
                  }
                }
              }
            }
          }
          break;

        case "4": //find linestrings via property name and value pair and transform into polygon
          System.out.print("EXAMPLE OF AVAILABLE PROPERTY NAMES\n");
          System.out.print(features.get(0).getKeys());
          System.out.println();

          System.out.print("Enter Property Name Linked To LineString\n>> ");
          propname = scan.next();

          System.out.print("Enter Property Value Linked To LineString\n>> ");
          String propval = scan.next();

          System.out.println("Line Strings Where " + propname + " = " + propval + " Are:");

          for (int i = 0; i < features.size(); ++i) //loops through each feature
          {
            if ((features.get(i).getType()).equals("LineString")) //checks if  point is a linestring
            {
              for (int j = 0; j < features.get(i).getVals().size(); ++j) //loops through each key and value pair (since both have the same length)
              {
                if ((features.get(i).getKeys().get(j)).equals(propname) && (features.get(i).getVals().get(j)).equals(propval)) //checks if the current key and value pair matches the user specified values
                {
                  System.out.println("\n");
                  System.out.println("Prior To Transformation");
                  System.out.println("Properties: " + features.get(i).getVals());
                  System.out.println("Geometry Type: " + features.get(i).getType());
                  System.out.println("Coordinates: " + features.get(i).getCoordinates()); //prints coordinates before transformation

                  //transformation
                  //adds first element to array (closes linestring) and adds new polygon to polygon array
                  features.get(i).getCoordinates().put(features.get(i).getCoordinates().get(0)); 
                  JSONArray temp = new JSONArray();
                  temp.put(features.get(i).getCoordinates());
                  features.get(i).setCoordinates(temp); //changes coordinates to polygon array format
                  features.get(i).setType("Polygon"); //changes the type in the object to polygon

                  System.out.print("\nAfter Polygon Transformation\n");
                  System.out.println("Properties: " + features.get(i).getVals());
                  System.out.println("Geometry Type: " + features.get(i).getType());
                  System.out.println("Coordinates: " + features.get(i).getCoordinates());

                  temp = null; //garbage collection
                }
              }
            }
          }
          break;

        case "5": //finds area of all polygons
          for (int i = 0; i < features.size(); ++i) //loops through each feature
          {
            //values that are reset to 0 after each loop
            double result = 0; //calculation value
            //coordinate values
            double x1 = 0;
            double x2 = 0;
            double y1 = 0;
            double y2 = 0;

            if ((features.get(i).getType()).equals("Polygon")) //checks if feature is a polygon
            {
              for (int j = 0; j < features.get(i).getCoordinates().length(); ++j) //loops through each polygon
              {
                for (int k = 0; k < (features.get(i).getCoordinates().getJSONArray(j).length())-1; ++k) //loops through each point in the current polygon
                {
                  x1 = features.get(i).getCoordinates().getJSONArray(j).getJSONArray(k).getDouble(0); 
                  y2 = features.get(i).getCoordinates().getJSONArray(j).getJSONArray(k+1).getDouble(1);
                  y1 = features.get(i).getCoordinates().getJSONArray(j).getJSONArray(k).getDouble(1);
                  x2 = features.get(i).getCoordinates().getJSONArray(j).getJSONArray(k+1).getDouble(0);
                  result = result + ((x1 * y2) - (y1 * x2)); //summation of formula applied to all points
                }
                result = result/2; //completes the formula by dividing the summation by 2
                System.out.println("Area Of Polygon " + features.get(i).getVals());
                System.out.println("= " + result);
              }
              System.out.println();
            }
          }
          break;

        case "6": //finds length of all linestrings
          for (int i = 0; i < features.size(); ++i) //loops through each feature
          {
            double result = 0;
            double x1 = 0;
            double x2 = 0;
            double y1 = 0;
            double y2 = 0;

            if ((features.get(i).getType()).equals("LineString")) //checks if feature is a linestring
            {
              for (int j = 0; j < (features.get(i).getCoordinates().length())-1; ++j) //loops through each point in the linestring
              {
                x2 = features.get(i).getCoordinates().getJSONArray(j+1).getDouble(0);
                x1 = features.get(i).getCoordinates().getJSONArray(j).getDouble(0);
                y2 = features.get(i).getCoordinates().getJSONArray(j+1).getDouble(1);
                y1 = features.get(i).getCoordinates().getJSONArray(j).getDouble(1);
                result = result + (((x2 - x1)*(x2 - x1)) + ((y2 - y1)*(y2 - y1))); //summation of formula
              }
              result = Math.sqrt(result); //completing the formula by finding the square root of the summation
              System.out.println("Length Of Line String " + features.get(i).getVals());
              System.out.println("= " + result);
              System.out.println();
            }
          }
          break;

        case "7":
          loopforever = 0;
          break;

        default:
          System.out.println("Invalid Option");
          break;
      }
      System.out.println("Press Enter To Continue");
      System.out.flush();  
      scan.nextLine(); //clears buffer
      scan.nextLine();
    }
    scan.close();
	}

  public static void clrScr() 
  {  
    System.out.print("\033[H\033[2J");  
    System.out.flush();  
  }  

}

