package main;

public class Version
{
	public static final Version version = new Version(0, 0, 1, "alpha");
	
	public final int major;
	public final int minor;
	public final int micro;
	public final String addTxt;
	
	public Version(int major, int minor, int micro, String addTxt)
	{
		this.major = major;
		this.minor = minor;
		this.micro = micro;
		this.addTxt = addTxt;
	}
	
	public int getMajorVersion()
	{
		return major;
	}
	public int getMinorVersion()
	{
		return minor;
	}
	public int getMicroVersion()
	{
		return micro;
	}
	public String getAdditionalText()
	{
		return addTxt;
	}
	public boolean matches(Version other)
	{
		return equals(other);
	}
	
	@Override
	public boolean equals(Object other)
	{
		if(other instanceof Version otherVer)
		{
			return otherVer.major == this.major
					&& otherVer.minor == this.minor
					&& otherVer.micro == this.micro
					&& otherVer.addTxt.equals(this.addTxt);
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		return "v" + major + "." + minor + "." + micro + "-" + addTxt;
	}
	
	@Override
	public Version clone()
	{
		return new Version(major, minor, micro, addTxt);
	}
}
