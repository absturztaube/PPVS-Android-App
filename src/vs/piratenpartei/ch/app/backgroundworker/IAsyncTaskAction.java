package vs.piratenpartei.ch.app.backgroundworker;

public interface IAsyncTaskAction<R>
{
	void onComplete(R pResult);
}
