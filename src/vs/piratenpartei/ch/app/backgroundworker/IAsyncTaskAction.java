package vs.piratenpartei.ch.app.backgroundworker;

public interface IAsyncTaskAction<R, T>
{
	void onComplete(R pResult, T pParameter);
}
