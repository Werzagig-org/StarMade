/*   1:    */package org.schema.schine.network.objects.remote;
/*   2:    */
/*   3:    */import java.io.DataInputStream;
/*   4:    */import java.io.DataOutputStream;
/*   5:    */import org.schema.schine.network.objects.NetworkObject;
/*   6:    */
/*   7:    */public class RemoteIntPrimitive
/*   8:    */  implements Streamable
/*   9:    */{
/*  10:    */  private boolean changed;
/*  11:    */  private NetworkChangeObserver observer;
/*  12:    */  protected boolean keepChanged;
/*  13:    */  private int value;
/*  14:    */  private final boolean onServer;
/*  15:    */  private boolean forcedClientSending;
/*  16:    */  
/*  17:    */  public RemoteIntPrimitive(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
/*  18:    */  {
/*  19: 19 */    this.value = paramInt;
/*  20:    */    
/*  21: 21 */    this.onServer = paramBoolean2;
/*  22: 22 */    this.changed = paramBoolean1;
/*  23:    */  }
/*  24:    */  
/*  25:    */  public RemoteIntPrimitive(int paramInt, boolean paramBoolean, NetworkObject paramNetworkObject) {
/*  26: 26 */    this(paramInt, paramBoolean, paramNetworkObject.isOnServer());
/*  27: 27 */    assert (paramNetworkObject != null);
/*  28:    */  }
/*  29:    */  
/*  30: 30 */  public RemoteIntPrimitive(int paramInt, boolean paramBoolean) { this(paramInt, false, paramBoolean); }
/*  31:    */  
/*  32:    */  public RemoteIntPrimitive(int paramInt, NetworkObject paramNetworkObject) {
/*  33: 33 */    this(paramInt, false, paramNetworkObject);
/*  34:    */  }
/*  35:    */  
/*  36:    */  public boolean hasChanged()
/*  37:    */  {
/*  38: 38 */    return this.changed;
/*  39:    */  }
/*  40:    */  
/*  41:    */  public void setObserver(NetworkChangeObserver paramNetworkChangeObserver)
/*  42:    */  {
/*  43: 43 */    this.observer = paramNetworkChangeObserver;
/*  44:    */  }
/*  45:    */  
/*  46:    */  public void setChanged(boolean paramBoolean)
/*  47:    */  {
/*  48: 48 */    this.changed = paramBoolean;
/*  49:    */  }
/*  50:    */  
/*  51:    */  public boolean keepChanged()
/*  52:    */  {
/*  53: 53 */    return this.keepChanged;
/*  54:    */  }
/*  55:    */  
/*  56:    */  public int byteLength()
/*  57:    */  {
/*  58: 58 */    return 4;
/*  59:    */  }
/*  60:    */  
/*  62:    */  public void fromByteStream(DataInputStream paramDataInputStream, int paramInt)
/*  63:    */  {
/*  64: 64 */    set(paramDataInputStream.readInt());
/*  65:    */  }
/*  66:    */  
/*  67:    */  public int toByteStream(DataOutputStream paramDataOutputStream)
/*  68:    */  {
/*  69: 69 */    paramDataOutputStream.writeInt(this.value);
/*  70: 70 */    return 4;
/*  71:    */  }
/*  72:    */  
/*  73: 73 */  public void forceClientUpdates() { this.forcedClientSending = true; }
/*  74:    */  
/*  75:    */  public void set(int paramInt) {
/*  76: 76 */    set(paramInt, this.forcedClientSending);
/*  77:    */  }
/*  78:    */  
/*  80: 80 */  public void set(Integer paramInteger) { set(paramInteger.intValue()); }
/*  81:    */  
/*  82:    */  public void set(int paramInt, boolean paramBoolean) {
/*  83: 83 */    if ((this.onServer) || (paramBoolean))
/*  84:    */    {
/*  85: 85 */      setChanged((hasChanged()) || (paramInt != this.value));
/*  86:    */    }
/*  87: 87 */    this.value = paramInt;
/*  88:    */    
/*  89: 89 */    if ((hasChanged()) && (this.observer != null))
/*  90:    */    {
/*  91: 91 */      this.observer.update(this);
/*  92:    */    }
/*  93:    */  }
/*  94:    */  
/*  96:    */  public void set(Integer paramInteger, boolean paramBoolean)
/*  97:    */  {
/*  98: 98 */    set(paramInteger);
/*  99:    */  }
/* 100:    */  
/* 101:    */  public int getInt() {
/* 102:102 */    return this.value;
/* 103:    */  }
/* 104:    */  
/* 105:    */  public Integer get() {
/* 106:106 */    return Integer.valueOf(this.value);
/* 107:    */  }
/* 108:    */  
/* 112:    */  public void cleanAtRelease() {}
/* 113:    */  
/* 116:    */  public boolean initialSynchUpdateOnly()
/* 117:    */  {
/* 118:118 */    return false;
/* 119:    */  }
/* 120:    */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     org.schema.schine.network.objects.remote.RemoteIntPrimitive
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */