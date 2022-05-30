
public class LockerSync
{
    private volatile boolean m_bIsLocked;
    // Объект синхронизации потоков
    private final Object m_sync;
    
    public LockerSync()
    {
        m_bIsLocked = false;
        m_sync = new Object();
    }
    
    void obtainLock()
    {
        String strThreadName = Thread.currentThread().getName();
        System.err.println(strThreadName + " awaiting lock");
        boolean bLockObtained = false;
        while (!bLockObtained)
        {
            synchronized (m_sync)
            {
                System.err.println(strThreadName + " entered synchronized");
                if (!m_bIsLocked)
                {
                    System.err.println(strThreadName + " obtained lock");
                    m_bIsLocked = true;
                    bLockObtained = true;
                }
                else
                {
                    System.err.println(strThreadName + " waiting for notification");
                    try
                    {
                        m_sync.wait();
                    }
                    catch (InterruptedException ex)
                    {
                    }
                    System.err.println(strThreadName + " got notification");
                }
            }
        }
    }

    void releaseLock()
    {
        String strThreadName = Thread.currentThread().getName();
        System.err.println(strThreadName + " releasing lock");
        synchronized (m_sync)
        {
            System.err.println(strThreadName + " released");
            m_bIsLocked = false;
            m_sync.notify();
        }
    }
    
}