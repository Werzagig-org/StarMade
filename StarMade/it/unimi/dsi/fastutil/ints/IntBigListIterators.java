/*   1:    */package it.unimi.dsi.fastutil.ints;
/*   2:    */
/*   3:    */import java.io.Serializable;
/*   4:    */import java.util.NoSuchElementException;
/*   5:    */
/*  53:    */public class IntBigListIterators
/*  54:    */{
/*  55:    */  public static class EmptyBigListIterator
/*  56:    */    extends AbstractIntBigListIterator
/*  57:    */    implements Serializable, Cloneable
/*  58:    */  {
/*  59:    */    public static final long serialVersionUID = -7046029254386353129L;
/*  60:    */    
/*  61: 61 */    public boolean hasNext() { return false; }
/*  62: 62 */    public boolean hasPrevious() { return false; }
/*  63: 63 */    public int nextInt() { throw new NoSuchElementException(); }
/*  64: 64 */    public int previousInt() { throw new NoSuchElementException(); }
/*  65: 65 */    public long nextIndex() { return 0L; }
/*  66: 66 */    public long previousIndex() { return -1L; }
/*  67: 67 */    public long skip(long n) { return 0L; }
/*  68: 68 */    public long back(long n) { return 0L; }
/*  69: 69 */    public Object clone() { return IntBigListIterators.EMPTY_BIG_LIST_ITERATOR; }
/*  70: 70 */    private Object readResolve() { return IntBigListIterators.EMPTY_BIG_LIST_ITERATOR; }
/*  71:    */  }
/*  72:    */  
/*  78: 78 */  public static final EmptyBigListIterator EMPTY_BIG_LIST_ITERATOR = new EmptyBigListIterator();
/*  79:    */  
/*  80:    */  private static class SingletonBigListIterator extends AbstractIntBigListIterator {
/*  81:    */    private final int element;
/*  82:    */    private int curr;
/*  83:    */    
/*  84: 84 */    public SingletonBigListIterator(int element) { this.element = element; }
/*  85:    */    
/*  86: 86 */    public boolean hasNext() { return this.curr == 0; }
/*  87: 87 */    public boolean hasPrevious() { return this.curr == 1; }
/*  88:    */    
/*  89: 89 */    public int nextInt() { if (!hasNext()) throw new NoSuchElementException();
/*  90: 90 */      this.curr = 1;
/*  91: 91 */      return this.element;
/*  92:    */    }
/*  93:    */    
/*  94: 94 */    public int previousInt() { if (!hasPrevious()) throw new NoSuchElementException();
/*  95: 95 */      this.curr = 0;
/*  96: 96 */      return this.element;
/*  97:    */    }
/*  98:    */    
/*  99: 99 */    public long nextIndex() { return this.curr; }
/* 100:    */    
/* 101:    */    public long previousIndex() {
/* 102:102 */      return this.curr - 1;
/* 103:    */    }
/* 104:    */  }
/* 105:    */  
/* 109:    */  public static IntBigListIterator singleton(int element)
/* 110:    */  {
/* 111:111 */    return new SingletonBigListIterator(element);
/* 112:    */  }
/* 113:    */  
/* 114:    */  public static class UnmodifiableBigListIterator extends AbstractIntBigListIterator
/* 115:    */  {
/* 116:    */    protected final IntBigListIterator i;
/* 117:    */    
/* 118:    */    public UnmodifiableBigListIterator(IntBigListIterator i)
/* 119:    */    {
/* 120:120 */      this.i = i;
/* 121:    */    }
/* 122:    */    
/* 123:123 */    public boolean hasNext() { return this.i.hasNext(); }
/* 124:124 */    public boolean hasPrevious() { return this.i.hasPrevious(); }
/* 125:125 */    public int nextInt() { return this.i.nextInt(); }
/* 126:126 */    public int previousInt() { return this.i.previousInt(); }
/* 127:127 */    public long nextIndex() { return this.i.nextIndex(); }
/* 128:128 */    public long previousIndex() { return this.i.previousIndex(); }
/* 129:    */    
/* 130:130 */    public Integer next() { return (Integer)this.i.next(); }
/* 131:131 */    public Integer previous() { return (Integer)this.i.previous(); }
/* 132:    */  }
/* 133:    */  
/* 138:    */  public static IntBigListIterator unmodifiable(IntBigListIterator i)
/* 139:    */  {
/* 140:140 */    return new UnmodifiableBigListIterator(i);
/* 141:    */  }
/* 142:    */  
/* 143:    */  public static class BigListIteratorListIterator extends AbstractIntBigListIterator
/* 144:    */  {
/* 145:    */    protected final IntListIterator i;
/* 146:    */    
/* 147:    */    protected BigListIteratorListIterator(IntListIterator i)
/* 148:    */    {
/* 149:149 */      this.i = i;
/* 150:    */    }
/* 151:    */    
/* 152:    */    private int intDisplacement(long n) {
/* 153:153 */      if ((n < -2147483648L) || (n > 2147483647L)) throw new IndexOutOfBoundsException("This big iterator is restricted to 32-bit displacements");
/* 154:154 */      return (int)n;
/* 155:    */    }
/* 156:    */    
/* 157:157 */    public void set(int ok) { this.i.set(ok); }
/* 158:158 */    public void add(int ok) { this.i.add(ok); }
/* 159:159 */    public int back(int n) { return this.i.back(n); }
/* 160:160 */    public long back(long n) { return this.i.back(intDisplacement(n)); }
/* 161:161 */    public void remove() { this.i.remove(); }
/* 162:162 */    public int skip(int n) { return this.i.skip(n); }
/* 163:163 */    public long skip(long n) { return this.i.skip(intDisplacement(n)); }
/* 164:164 */    public boolean hasNext() { return this.i.hasNext(); }
/* 165:165 */    public boolean hasPrevious() { return this.i.hasPrevious(); }
/* 166:166 */    public int nextInt() { return this.i.nextInt(); }
/* 167:167 */    public int previousInt() { return this.i.previousInt(); }
/* 168:168 */    public long nextIndex() { return this.i.nextIndex(); }
/* 169:169 */    public long previousIndex() { return this.i.previousIndex(); }
/* 170:    */  }
/* 171:    */  
/* 175:    */  public static IntBigListIterator asBigListIterator(IntListIterator i)
/* 176:    */  {
/* 177:177 */    return new BigListIteratorListIterator(i);
/* 178:    */  }
/* 179:    */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.ints.IntBigListIterators
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */