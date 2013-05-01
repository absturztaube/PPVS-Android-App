package vs.piratenpartei.ch.app.redmine;

import java.util.ArrayList;

public class TrackerCollection extends ArrayList<Tracker>
{
	private static final long serialVersionUID = -5661630015168321849L;
	
	public boolean containsTracker(int pId)
	{
		for(Tracker tracker : this)
		{
			if(tracker.getId() == pId)
				return true;
		}
		return false;
	}
}
