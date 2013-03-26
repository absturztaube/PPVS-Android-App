package vs.piratenpartei.ch.app.redmine;

import java.util.Date;

public class IssueDetailItem 
{	
	private String _author;
	private String _assignee;
	private String _priority;
	private Date _startDate;
	private Date _dueDate;
	private Date _createdOn;
	private Date _updatedOn;
	private String _description;
	private int _progress;
	private String _status;
	private String _estimatedHours;
	private JournalItemCollection _journal = new JournalItemCollection();
	
	public String getAuthor()
	{
		return this._author;
	}
	
	public void setAuthor(String pAuthor)
	{
		this._author = pAuthor;
	}
	
	public String getAssignedTo()
	{
		return this._assignee;
	}
	
	public void setAssignedTo(String pAssignedTo)
	{
		this._assignee = pAssignedTo;
	}
	
	public String getPriority()
	{
		return this._priority;
	}
	
	public void setPriority(String pPriority)
	{
		this._priority = pPriority;
	}
	
	public Date getStartDate()
	{
		return this._startDate;
	}
	
	public void setStartDate(Date pStartDate)
	{
		this._startDate = pStartDate;
	}
	
	public Date getDueDate()
	{
		return this._dueDate;
	}
	
	public void setDueDate(Date pDueDate)
	{
		this._dueDate = pDueDate;
	}
	
	public Date getCreatedOn()
	{
		return this._createdOn;
	}
	
	public void setCreatedOn(Date pCreatedOn)
	{
		this._createdOn = pCreatedOn;
	}
	
	public Date getUpdatedOn()
	{
		return this._updatedOn;
	}
	
	public void setUpdatedOn(Date pUpdatedOn)
	{
		this._updatedOn = pUpdatedOn;
	}
	
	public String getDescription()
	{
		return this._description;
	}
	
	public void setDescription(String pDescription)
	{
		this._description = pDescription;
	}
	
	public int getProgress()
	{
		return this._progress;
	}
	
	public void setProgress(int pProgress)
	{
		this._progress = pProgress;
	}
	
	public String getStatus()
	{
		return this._status;
	}
	
	public void setStatus(String pStatus)
	{
		this._status = pStatus;
	}
	
	public String getEstimatedHours()
	{
		return this._estimatedHours;
	}
	
	public void setEstimatedHours(String pEstimatedHours)
	{
		this._estimatedHours = pEstimatedHours;
	}
	
	public JournalItemCollection getJournal()
	{
		return this._journal;
	}
	
	public void setJournal(JournalItemCollection pJournal)
	{
		this._journal = pJournal;
	}
	
	public void addJournalItem(JournalItem pJournalItem)
	{
		this._journal.add(pJournalItem);
	}
}
