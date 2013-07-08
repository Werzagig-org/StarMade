/*   1:    */package it.unimi.dsi.fastutil.booleans;
/*   2:    */
/*   3:    */import it.unimi.dsi.fastutil.objects.ObjectArrays;
/*   4:    */import java.io.Serializable;
/*   5:    */import java.util.Collection;
/*   6:    */
/*  55:    */public class BooleanCollections
/*  56:    */{
/*  57:    */  public static abstract class EmptyCollection
/*  58:    */    extends AbstractBooleanCollection
/*  59:    */  {
/*  60: 60 */    public boolean add(boolean k) { throw new UnsupportedOperationException(); }
/*  61: 61 */    public boolean contains(boolean k) { return false; }
/*  62: 62 */    public Object[] toArray() { return ObjectArrays.EMPTY_ARRAY; }
/*  63: 63 */    public boolean[] toBooleanArray(boolean[] a) { return a; }
/*  64: 64 */    public boolean[] toBooleanArray() { return BooleanArrays.EMPTY_ARRAY; }
/*  65: 65 */    public boolean rem(boolean k) { throw new UnsupportedOperationException(); }
/*  66: 66 */    public boolean addAll(BooleanCollection c) { throw new UnsupportedOperationException(); }
/*  67: 67 */    public boolean removeAll(BooleanCollection c) { throw new UnsupportedOperationException(); }
/*  68: 68 */    public boolean retainAll(BooleanCollection c) { throw new UnsupportedOperationException(); }
/*  69: 69 */    public boolean containsAll(BooleanCollection c) { return c.isEmpty(); }
/*  70:    */    
/*  71: 71 */    public BooleanBidirectionalIterator iterator() { return BooleanIterators.EMPTY_ITERATOR; }
/*  72: 72 */    public int size() { return 0; }
/*  73:    */    public void clear() {}
/*  74: 74 */    public int hashCode() { return 0; }
/*  75:    */    
/*  76: 76 */    public boolean equals(Object o) { if (o == this) return true;
/*  77: 77 */      if (!(o instanceof Collection)) return false;
/*  78: 78 */      return ((Collection)o).isEmpty();
/*  79:    */    }
/*  80:    */  }
/*  81:    */  
/*  82:    */  public static class SynchronizedCollection
/*  83:    */    implements BooleanCollection, Serializable
/*  84:    */  {
/*  85:    */    public static final long serialVersionUID = -7046029254386353129L;
/*  86:    */    protected final BooleanCollection collection;
/*  87:    */    protected final Object sync;
/*  88:    */    
/*  89:    */    protected SynchronizedCollection(BooleanCollection c, Object sync)
/*  90:    */    {
/*  91: 91 */      if (c == null) throw new NullPointerException();
/*  92: 92 */      this.collection = c;
/*  93: 93 */      this.sync = sync;
/*  94:    */    }
/*  95:    */    
/*  96:    */    protected SynchronizedCollection(BooleanCollection c) {
/*  97: 97 */      if (c == null) throw new NullPointerException();
/*  98: 98 */      this.collection = c;
/*  99: 99 */      this.sync = this;
/* 100:    */    }
/* 101:    */    
/* 102:102 */    public int size() { synchronized (this.sync) { return this.collection.size(); } }
/* 103:103 */    public boolean isEmpty() { synchronized (this.sync) { return this.collection.isEmpty(); } }
/* 104:104 */    public boolean contains(boolean o) { synchronized (this.sync) { return this.collection.contains(o); } }
/* 105:    */    
/* 106:106 */    public boolean[] toBooleanArray() { synchronized (this.sync) { return this.collection.toBooleanArray();
/* 107:    */      } }
/* 108:    */    
/* 109:109 */    public Object[] toArray() { synchronized (this.sync) { return this.collection.toArray(); } }
/* 110:110 */    public boolean[] toBooleanArray(boolean[] a) { synchronized (this.sync) { return this.collection.toBooleanArray(a); } }
/* 111:111 */    public boolean[] toArray(boolean[] a) { synchronized (this.sync) { return this.collection.toBooleanArray(a); } }
/* 112:    */    
/* 113:113 */    public boolean addAll(BooleanCollection c) { synchronized (this.sync) { return this.collection.addAll(c); } }
/* 114:114 */    public boolean containsAll(BooleanCollection c) { synchronized (this.sync) { return this.collection.containsAll(c); } }
/* 115:115 */    public boolean removeAll(BooleanCollection c) { synchronized (this.sync) { return this.collection.removeAll(c); } }
/* 116:116 */    public boolean retainAll(BooleanCollection c) { synchronized (this.sync) { return this.collection.retainAll(c); } }
/* 117:    */    
/* 118:118 */    public boolean add(Boolean k) { synchronized (this.sync) { return this.collection.add(k); } }
/* 119:119 */    public boolean contains(Object k) { synchronized (this.sync) { return this.collection.contains(k);
/* 120:    */      } }
/* 121:    */    
/* 122:122 */    public <T> T[] toArray(T[] a) { synchronized (this.sync) { return this.collection.toArray(a); } }
/* 123:    */    
/* 124:124 */    public BooleanIterator iterator() { return this.collection.iterator(); }
/* 125:    */    
/* 126:    */    @Deprecated
/* 127:127 */    public BooleanIterator booleanIterator() { return iterator(); }
/* 128:    */    
/* 129:129 */    public boolean add(boolean k) { synchronized (this.sync) { return this.collection.add(k); } }
/* 130:130 */    public boolean rem(boolean k) { synchronized (this.sync) { return this.collection.rem(k); } }
/* 131:131 */    public boolean remove(Object ok) { synchronized (this.sync) { return this.collection.remove(ok); } }
/* 132:    */    
/* 133:133 */    public boolean addAll(Collection<? extends Boolean> c) { synchronized (this.sync) { return this.collection.addAll(c); } }
/* 134:134 */    public boolean containsAll(Collection<?> c) { synchronized (this.sync) { return this.collection.containsAll(c); } }
/* 135:135 */    public boolean removeAll(Collection<?> c) { synchronized (this.sync) { return this.collection.removeAll(c); } }
/* 136:136 */    public boolean retainAll(Collection<?> c) { synchronized (this.sync) { return this.collection.retainAll(c); } }
/* 137:    */    
/* 138:138 */    public void clear() { synchronized (this.sync) { this.collection.clear(); } }
/* 139:139 */    public String toString() { synchronized (this.sync) { return this.collection.toString();
/* 140:    */      }
/* 141:    */    }
/* 142:    */  }
/* 143:    */  
/* 147:    */  public static BooleanCollection synchronize(BooleanCollection c)
/* 148:    */  {
/* 149:149 */    return new SynchronizedCollection(c);
/* 150:    */  }
/* 151:    */  
/* 157:    */  public static BooleanCollection synchronize(BooleanCollection c, Object sync)
/* 158:    */  {
/* 159:159 */    return new SynchronizedCollection(c, sync);
/* 160:    */  }
/* 161:    */  
/* 163:    */  public static class UnmodifiableCollection
/* 164:    */    implements BooleanCollection, Serializable
/* 165:    */  {
/* 166:    */    public static final long serialVersionUID = -7046029254386353129L;
/* 167:    */    protected final BooleanCollection collection;
/* 168:    */    
/* 169:    */    protected UnmodifiableCollection(BooleanCollection c)
/* 170:    */    {
/* 171:171 */      if (c == null) throw new NullPointerException();
/* 172:172 */      this.collection = c;
/* 173:    */    }
/* 174:    */    
/* 175:175 */    public int size() { return this.collection.size(); }
/* 176:176 */    public boolean isEmpty() { return this.collection.isEmpty(); }
/* 177:177 */    public boolean contains(boolean o) { return this.collection.contains(o); }
/* 178:    */    
/* 179:179 */    public BooleanIterator iterator() { return BooleanIterators.unmodifiable(this.collection.iterator()); }
/* 180:    */    
/* 181:    */    @Deprecated
/* 182:182 */    public BooleanIterator booleanIterator() { return iterator(); }
/* 183:    */    
/* 184:184 */    public boolean add(boolean k) { throw new UnsupportedOperationException(); }
/* 185:185 */    public boolean remove(Object ok) { throw new UnsupportedOperationException(); }
/* 186:    */    
/* 187:187 */    public boolean addAll(Collection<? extends Boolean> c) { throw new UnsupportedOperationException(); }
/* 188:188 */    public boolean containsAll(Collection<?> c) { return this.collection.containsAll(c); }
/* 189:189 */    public boolean removeAll(Collection<?> c) { throw new UnsupportedOperationException(); }
/* 190:190 */    public boolean retainAll(Collection<?> c) { throw new UnsupportedOperationException(); }
/* 191:    */    
/* 192:192 */    public void clear() { throw new UnsupportedOperationException(); }
/* 193:193 */    public String toString() { return this.collection.toString(); }
/* 194:    */    
/* 195:195 */    public <T> T[] toArray(T[] a) { return this.collection.toArray(a); }
/* 196:    */    
/* 197:197 */    public Object[] toArray() { return this.collection.toArray(); }
/* 198:    */    
/* 200:200 */    public boolean[] toBooleanArray() { return this.collection.toBooleanArray(); }
/* 201:201 */    public boolean[] toBooleanArray(boolean[] a) { return this.collection.toBooleanArray(a); }
/* 202:202 */    public boolean[] toArray(boolean[] a) { return this.collection.toArray(a); }
/* 203:203 */    public boolean rem(boolean k) { throw new UnsupportedOperationException(); }
/* 204:    */    
/* 205:205 */    public boolean addAll(BooleanCollection c) { throw new UnsupportedOperationException(); }
/* 206:206 */    public boolean containsAll(BooleanCollection c) { return this.collection.containsAll(c); }
/* 207:207 */    public boolean removeAll(BooleanCollection c) { throw new UnsupportedOperationException(); }
/* 208:208 */    public boolean retainAll(BooleanCollection c) { throw new UnsupportedOperationException(); }
/* 209:    */    
/* 210:210 */    public boolean add(Boolean k) { throw new UnsupportedOperationException(); }
/* 211:211 */    public boolean contains(Object k) { return this.collection.contains(k); }
/* 212:    */  }
/* 213:    */  
/* 220:    */  public static BooleanCollection unmodifiable(BooleanCollection c)
/* 221:    */  {
/* 222:222 */    return new UnmodifiableCollection(c);
/* 223:    */  }
/* 224:    */  
/* 225:    */  public static class IterableCollection
/* 226:    */    extends AbstractBooleanCollection implements Serializable
/* 227:    */  {
/* 228:    */    public static final long serialVersionUID = -7046029254386353129L;
/* 229:    */    protected final BooleanIterable iterable;
/* 230:    */    
/* 231:    */    protected IterableCollection(BooleanIterable iterable)
/* 232:    */    {
/* 233:233 */      if (iterable == null) throw new NullPointerException();
/* 234:234 */      this.iterable = iterable;
/* 235:    */    }
/* 236:    */    
/* 237:    */    public int size() {
/* 238:238 */      int c = 0;
/* 239:239 */      BooleanIterator iterator = iterator();
/* 240:240 */      while (iterator.hasNext()) {
/* 241:241 */        iterator.next();
/* 242:242 */        c++;
/* 243:    */      }
/* 244:    */      
/* 245:245 */      return c;
/* 246:    */    }
/* 247:    */    
/* 248:248 */    public boolean isEmpty() { return this.iterable.iterator().hasNext(); }
/* 249:249 */    public BooleanIterator iterator() { return this.iterable.iterator(); }
/* 250:    */    
/* 251:    */    @Deprecated
/* 252:252 */    public BooleanIterator booleanIterator() { return iterator(); }
/* 253:    */  }
/* 254:    */  
/* 261:    */  public static BooleanCollection asCollection(BooleanIterable iterable)
/* 262:    */  {
/* 263:263 */    if ((iterable instanceof BooleanCollection)) return (BooleanCollection)iterable;
/* 264:264 */    return new IterableCollection(iterable);
/* 265:    */  }
/* 266:    */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.booleans.BooleanCollections
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */