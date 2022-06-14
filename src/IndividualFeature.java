import org.json.JSONArray;
import java.util.ArrayList;

public class IndividualFeature {
    private JSONArray keys;
	private ArrayList<String> vals;
	private String type;
	private JSONArray coordinates;

	public IndividualFeature(JSONArray keyprop, ArrayList<String> valprop, String geotype, JSONArray geocoords) 
	{
		this.keys = keyprop;
		this.vals = valprop;
		this.type = geotype;
		this.coordinates = geocoords;
	}

    public float getLengthOfLine()
    {
        if (type.equals("LineString"))
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

	public JSONArray getKeys()
	{
		return this.keys;
	}

	public ArrayList<String> getVals()
	{
		return this.vals;
	}

	public String getType()
	{
		return this.type;
	}

	public JSONArray getCoordinates()
	{
		return this.coordinates;
	}

	public void setType(String val)
	{
		this.type = val;
	}

	public void setCoordinates(JSONArray val)
	{
		this.coordinates = val;
	}
}
