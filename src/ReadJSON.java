import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
 
public class ReadJSON 
{ 
	private ArrayList<IndividualFeature> individualfeatures = new ArrayList<IndividualFeature>(); //creates new arraylist to store collection of feature objects
	private String jsonfile; //The JSON path passed into the function
	public ReadJSON(String filepath) 
	{
		this.jsonfile = filepath; //linking to object
		try 
		{
			String rawtext = new String((Files.readAllBytes(Paths.get(this.jsonfile)))); //storing the raw unprocessed text from the json
			JSONObject geoobj = new JSONObject(rawtext); //using the raw unprocessed text to create a JsonObject

			JSONArray features = geoobj.getJSONArray("features"); //creating an array of features

			if (geoobj.getString("type").equals("FeatureCollection")) //verifies if the json contains a feature collection
			{
				System.out.println("JSON Valid {GeoJSON FeatureCollection}");
				for (int i = 0; i < features.length(); ++i) //loops through each feature object and adds each key to  user defined structure using a constructor and an array of objects
				{
					JSONObject element = features.getJSONObject(i); //gets the object in the current index of the features array
					JSONObject propertyobj = element.getJSONObject("properties"); //finds the properties key of that specific object and stores in variable
					JSONArray propertykeys = propertyobj.names(); //stores the keys of the property object
					ArrayList<String> propertyvals = new ArrayList<String>(); //stores the values of the property object
					JSONObject geometry = element.getJSONObject("geometry"); //stores the geometric features of the feature
	
					for (int j = 0; j < propertykeys.length (); ++j) //adds each key to the propertykeys array
					{
						String key = propertykeys.getString(j); 
						propertyvals.add(propertyobj.get(key).toString());
					 }
	
					String geometrytype = geometry.getString("type"); //gets the type of the geometric feature and stores into variable
					JSONArray coordinates = geometry.getJSONArray("coordinates"); //gets the array of coordinates and stores into variable
					individualfeatures.add( new IndividualFeature(propertykeys,propertyvals,geometrytype,coordinates) ); //uses all the previously obtained values, constructs a new object matching those features and adds them to an array
				}
			}
			else
			{
				System.out.println("Error: JSON is not GeoJSON");
				System.exit(0);
			}
		} 
		catch (Exception e) //catches errors
		{
			System.out.println("Error: File not found");
			System.exit(0);
		}
	}

	//returns the array of Individual Features
	public ArrayList<IndividualFeature> getIndividualFeatures()
	{
		return individualfeatures;
	}
}




