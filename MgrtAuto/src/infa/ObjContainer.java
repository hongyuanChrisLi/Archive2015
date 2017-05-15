package infa;

public class ObjContainer {
	
	private String folder;
	private String object;
	private String type;
	private String subtype;
	private final static int FOLDER_IDX = 10;
	private final static int OBJ_IDX = 11;
	private final static int TYPE_IDX = 12;
	private final static int SUBTYPE_IDX = 13;
	
	public ObjContainer (String in_str ) {
		
		LoadObj(in_str);
	}
	
	private void LoadObj(String in_str) {
		
		String line_upd = in_str.replace(":",",");
		String[] obj_dtl = line_upd.split(",");
		
		folder = obj_dtl[FOLDER_IDX];
		object = obj_dtl[OBJ_IDX];
		type = obj_dtl[TYPE_IDX];
		subtype = obj_dtl[SUBTYPE_IDX];
		
		//String[] line3 = Arrays.copyOfRange(line2, START_IDX, END_IDX);
	}
	
	public String getValue (String in_field ){
		
		String res = null;
		try
		{
			switch (in_field) {
				case "folder": 
					res = this.folder; 
					break;
				case "object": 
					res = this.object;
					break;
				case "type" :
					res = this.type;
					break;
				case "subtype" :
					res = this.subtype;
					break;
				default:
			}
		}catch (NullPointerException npe){
			System.out.println("Incorrect Input Field");
		}
		
		return res;
	}
	
	public void print () {
		System.out.println (folder + ", " + object + ", " + type + ", " + subtype );
	}
	
}