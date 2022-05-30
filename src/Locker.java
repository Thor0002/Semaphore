
public class Locker
{
	private volatile boolean m_bIsLocked;

	public Locker()
	{
		m_bIsLocked = false;
	}

	void obtainLock()
	{
		String strThreadName = Thread.currentThread().getName();
		System.err.println(strThreadName + " awaiting lock");
		boolean bLockObtained = false;
		while (!bLockObtained)
		{
			synchronized (this)
			{
				System.err.println(strThreadName + " entered synchronized");
				if (!m_bIsLocked)
				{
					System.err.println(strThreadName + " obtained lock");
					m_bIsLocked = true;
					bLockObtained = true;
					break;
				}
			}
			System.err.println(strThreadName + " left synchronized, sleeping");
			try
			{
				Thread.sleep(50);
			}
			catch (InterruptedException ex)
			{
			}
		}
	}

	void releaseLock()
	{
		String strThreadName = Thread.currentThread().getName();
		System.err.println(strThreadName + " releasing lock");
		synchronized (this)
		{
			System.err.println(strThreadName + " released");
			m_bIsLocked = false;
		}
	}

}