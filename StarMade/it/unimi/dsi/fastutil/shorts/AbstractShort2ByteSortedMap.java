/*   1:    */package it.unimi.dsi.fastutil.shorts;
/*   2:    */
/*   3:    */import it.unimi.dsi.fastutil.bytes.AbstractByteCollection;
/*   4:    */import it.unimi.dsi.fastutil.bytes.AbstractByteIterator;
/*   5:    */import it.unimi.dsi.fastutil.bytes.ByteCollection;
/*   6:    */import it.unimi.dsi.fastutil.bytes.ByteIterator;
/*   7:    */import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
/*   8:    */import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
/*   9:    */import java.util.Map.Entry;
/*  10:    */
/*  51:    */public abstract class AbstractShort2ByteSortedMap
/*  52:    */  extends AbstractShort2ByteMap
/*  53:    */  implements Short2ByteSortedMap
/*  54:    */{
/*  55:    */  public static final long serialVersionUID = -1773560792952436569L;
/*  56:    */  
/*  57:    */  public Short2ByteSortedMap headMap(Short to)
/*  58:    */  {
/*  59: 59 */    return headMap(to.shortValue());
/*  60:    */  }
/*  61:    */  
/*  62:    */  public Short2ByteSortedMap tailMap(Short from) {
/*  63: 63 */    return tailMap(from.shortValue());
/*  64:    */  }
/*  65:    */  
/*  66:    */  public Short2ByteSortedMap subMap(Short from, Short to) {
/*  67: 67 */    return subMap(from.shortValue(), to.shortValue());
/*  68:    */  }
/*  69:    */  
/*  70:    */  public Short firstKey() {
/*  71: 71 */    return Short.valueOf(firstShortKey());
/*  72:    */  }
/*  73:    */  
/*  74:    */  public Short lastKey() {
/*  75: 75 */    return Short.valueOf(lastShortKey());
/*  76:    */  }
/*  77:    */  
/*  89: 89 */  public ShortSortedSet keySet() { return new KeySet(); }
/*  90:    */  
/*  91:    */  protected class KeySet extends AbstractShortSortedSet { protected KeySet() {}
/*  92:    */    
/*  93: 93 */    public boolean contains(short k) { return AbstractShort2ByteSortedMap.this.containsKey(k); }
/*  94: 94 */    public int size() { return AbstractShort2ByteSortedMap.this.size(); }
/*  95: 95 */    public void clear() { AbstractShort2ByteSortedMap.this.clear(); }
/*  96: 96 */    public ShortComparator comparator() { return AbstractShort2ByteSortedMap.this.comparator(); }
/*  97: 97 */    public short firstShort() { return AbstractShort2ByteSortedMap.this.firstShortKey(); }
/*  98: 98 */    public short lastShort() { return AbstractShort2ByteSortedMap.this.lastShortKey(); }
/*  99: 99 */    public ShortSortedSet headSet(short to) { return AbstractShort2ByteSortedMap.this.headMap(to).keySet(); }
/* 100:100 */    public ShortSortedSet tailSet(short from) { return AbstractShort2ByteSortedMap.this.tailMap(from).keySet(); }
/* 101:101 */    public ShortSortedSet subSet(short from, short to) { return AbstractShort2ByteSortedMap.this.subMap(from, to).keySet(); }
/* 102:    */    
/* 103:103 */    public ShortBidirectionalIterator iterator(short from) { return new AbstractShort2ByteSortedMap.KeySetIterator(AbstractShort2ByteSortedMap.this.entrySet().iterator(new AbstractShort2ByteMap.BasicEntry(from, (byte)0))); }
/* 104:104 */    public ShortBidirectionalIterator iterator() { return new AbstractShort2ByteSortedMap.KeySetIterator(AbstractShort2ByteSortedMap.this.entrySet().iterator()); }
/* 105:    */  }
/* 106:    */  
/* 109:    */  protected static class KeySetIterator
/* 110:    */    extends AbstractShortBidirectionalIterator
/* 111:    */  {
/* 112:    */    protected final ObjectBidirectionalIterator<Map.Entry<Short, Byte>> i;
/* 113:    */    
/* 116:    */    public KeySetIterator(ObjectBidirectionalIterator<Map.Entry<Short, Byte>> i)
/* 117:    */    {
/* 118:118 */      this.i = i;
/* 119:    */    }
/* 120:    */    
/* 121:121 */    public short nextShort() { return ((Short)((Map.Entry)this.i.next()).getKey()).shortValue(); }
/* 122:122 */    public short previousShort() { return ((Short)((Map.Entry)this.i.previous()).getKey()).shortValue(); }
/* 123:    */    
/* 124:124 */    public boolean hasNext() { return this.i.hasNext(); }
/* 125:125 */    public boolean hasPrevious() { return this.i.hasPrevious(); }
/* 126:    */  }
/* 127:    */  
/* 143:143 */  public ByteCollection values() { return new ValuesCollection(); }
/* 144:    */  
/* 145:    */  protected class ValuesCollection extends AbstractByteCollection {
/* 146:    */    protected ValuesCollection() {}
/* 147:    */    
/* 148:148 */    public ByteIterator iterator() { return new AbstractShort2ByteSortedMap.ValuesIterator(AbstractShort2ByteSortedMap.this.entrySet().iterator()); }
/* 149:149 */    public boolean contains(byte k) { return AbstractShort2ByteSortedMap.this.containsValue(k); }
/* 150:150 */    public int size() { return AbstractShort2ByteSortedMap.this.size(); }
/* 151:151 */    public void clear() { AbstractShort2ByteSortedMap.this.clear(); }
/* 152:    */  }
/* 153:    */  
/* 156:    */  protected static class ValuesIterator
/* 157:    */    extends AbstractByteIterator
/* 158:    */  {
/* 159:    */    protected final ObjectBidirectionalIterator<Map.Entry<Short, Byte>> i;
/* 160:    */    
/* 163:    */    public ValuesIterator(ObjectBidirectionalIterator<Map.Entry<Short, Byte>> i)
/* 164:    */    {
/* 165:165 */      this.i = i;
/* 166:    */    }
/* 167:    */    
/* 168:168 */    public byte nextByte() { return ((Byte)((Map.Entry)this.i.next()).getValue()).byteValue(); }
/* 169:169 */    public boolean hasNext() { return this.i.hasNext(); }
/* 170:    */  }
/* 171:    */  
/* 172:    */  public ObjectSortedSet<Map.Entry<Short, Byte>> entrySet()
/* 173:    */  {
/* 174:174 */    return short2ByteEntrySet();
/* 175:    */  }
/* 176:    */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.shorts.AbstractShort2ByteSortedMap
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */