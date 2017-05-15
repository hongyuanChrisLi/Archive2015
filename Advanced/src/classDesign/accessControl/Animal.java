package classDesign.accessControl;


public class Animal extends AbstractAnimal
{
	
	private String name;
	
	@Override
	public void eat ()
	{
		
	}
	
	@Override
	public void sleep ()
	{
		
	}
	
	public Animal (String name)
	{
		this.name = name;
	}
	
	
	@Override
	public int hashCode ()
	{
		int hash = 7;
		int strlen = name.length();
		for ( int i = 0; i < strlen; i++ )
		{
			hash = hash * 11 + name.charAt(i);
		}

		return hash;
	}
	
	@Override
	public boolean equals (Object obj)
	{
		if ( obj instanceof Animal )
		{
			Animal animal_beta = (Animal) obj;
			
			if ( animal_beta.hashCode() == this.hashCode() )
				return true;
		}
		
		
		return false;
		
	}
	
	
	@Override
	public String toString()
	{
		return "This is: " + name;
	}
	
	public void print_name () 
	{
		System.out.println ("The name: " + name);
	}
	
	
}