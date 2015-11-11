package org.xdams.utility.text.findreplace;

public interface ITransactionalReplacer extends IReplacer {
    public void saveCheckPoint();
    public void restoreCheckPoint(int checkPointNum);
    public void restoreLastCheckPoint();
    public void removeCheckPoint(int checkPointNum);
    public void removeAllCheckPoints();
}
