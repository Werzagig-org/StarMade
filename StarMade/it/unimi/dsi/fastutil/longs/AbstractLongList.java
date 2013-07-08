/*   1:    */package it.unimi.dsi.fastutil.longs;
/*   2:    */
/*   3:    */import it.unimi.dsi.fastutil.HashCommon;
/*   4:    */import java.io.Serializable;
/*   5:    */import java.util.Collection;
/*   6:    */import java.util.Iterator;
/*   7:    */import java.util.List;
/*   8:    */import java.util.ListIterator;
/*   9:    */import java.util.NoSuchElementException;
/*  10:    */
/*  56:    */public abstract class AbstractLongList
/*  57:    */  extends AbstractLongCollection
/*  58:    */  implements LongList, LongStack
/*  59:    */{
/*  60:    */  protected void ensureIndex(int index)
/*  61:    */  {
/*  62: 62 */    if (index < 0) throw new IndexOutOfBoundsException(new StringBuilder().append("Index (").append(index).append(") is negative").toString());
/*  63: 63 */    if (index > size()) { throw new IndexOutOfBoundsException(new StringBuilder().append("Index (").append(index).append(") is greater than list size (").append(size()).append(")").toString());
/*  64:    */    }
/*  65:    */  }
/*  66:    */  
/*  69:    */  protected void ensureRestrictedIndex(int index)
/*  70:    */  {
/*  71: 71 */    if (index < 0) throw new IndexOutOfBoundsException(new StringBuilder().append("Index (").append(index).append(") is negative").toString());
/*  72: 72 */    if (index >= size()) throw new IndexOutOfBoundsException(new StringBuilder().append("Index (").append(index).append(") is greater than or equal to list size (").append(size()).append(")").toString());
/*  73:    */  }
/*  74:    */  
/*  75: 75 */  public void add(int index, long k) { throw new UnsupportedOperationException(); }
/*  76:    */  
/*  77:    */  public boolean add(long k) {
/*  78: 78 */    add(size(), k);
/*  79: 79 */    return true;
/*  80:    */  }
/*  81:    */  
/*  82: 82 */  public long removeLong(int i) { throw new UnsupportedOperationException(); }
/*  83:    */  
/*  85: 85 */  public long set(int index, long k) { throw new UnsupportedOperationException(); }
/*  86:    */  
/*  87:    */  public boolean addAll(int index, Collection<? extends Long> c) {
/*  88: 88 */    ensureIndex(index);
/*  89: 89 */    int n = c.size();
/*  90: 90 */    if (n == 0) return false;
/*  91: 91 */    Iterator<? extends Long> i = c.iterator();
/*  92: 92 */    while (n-- != 0) add(index++, (Long)i.next());
/*  93: 93 */    return true;
/*  94:    */  }
/*  95:    */  
/*  96:    */  public boolean addAll(Collection<? extends Long> c) {
/*  97: 97 */    return addAll(size(), c);
/*  98:    */  }
/*  99:    */  
/* 100:    */  @Deprecated
/* 101:    */  public LongListIterator longListIterator() {
/* 102:102 */    return listIterator();
/* 103:    */  }
/* 104:    */  
/* 105:    */  @Deprecated
/* 106:    */  public LongListIterator longListIterator(int index) {
/* 107:107 */    return listIterator(index);
/* 108:    */  }
/* 109:    */  
/* 110:110 */  public LongListIterator iterator() { return listIterator(); }
/* 111:    */  
/* 112:    */  public LongListIterator listIterator() {
/* 113:113 */    return listIterator(0);
/* 114:    */  }
/* 115:    */  
/* 116:116 */  public LongListIterator listIterator(final int index) { new AbstractLongListIterator() {
/* 117:117 */      int last = -1; int pos = index;
/* 118:118 */      public boolean hasNext() { return this.pos < AbstractLongList.this.size(); }
/* 119:119 */      public boolean hasPrevious() { return this.pos > 0; }
/* 120:120 */      public long nextLong() { if (!hasNext()) throw new NoSuchElementException(); return AbstractLongList.this.getLong(this.last = this.pos++); }
/* 121:121 */      public long previousLong() { if (!hasPrevious()) throw new NoSuchElementException(); return AbstractLongList.this.getLong(this.last = --this.pos); }
/* 122:122 */      public int nextIndex() { return this.pos; }
/* 123:123 */      public int previousIndex() { return this.pos - 1; }
/* 124:    */      
/* 125:125 */      public void add(long k) { if (this.last == -1) throw new IllegalStateException();
/* 126:126 */        AbstractLongList.this.add(this.pos++, k);
/* 127:127 */        this.last = -1;
/* 128:    */      }
/* 129:    */      
/* 130:130 */      public void set(long k) { if (this.last == -1) throw new IllegalStateException();
/* 131:131 */        AbstractLongList.this.set(this.last, k);
/* 132:    */      }
/* 133:    */      
/* 134:134 */      public void remove() { if (this.last == -1) throw new IllegalStateException();
/* 135:135 */        AbstractLongList.this.removeLong(this.last);
/* 136:    */        
/* 137:137 */        if (this.last < this.pos) this.pos -= 1;
/* 138:138 */        this.last = -1;
/* 139:    */      }
/* 140:    */    }; }
/* 141:    */  
/* 144:    */  public boolean contains(long k)
/* 145:    */  {
/* 146:146 */    return indexOf(k) >= 0;
/* 147:    */  }
/* 148:    */  
/* 149:    */  public int indexOf(long k) {
/* 150:150 */    LongListIterator i = listIterator();
/* 151:    */    
/* 152:152 */    while (i.hasNext()) {
/* 153:153 */      long e = i.nextLong();
/* 154:154 */      if (k == e) return i.previousIndex();
/* 155:    */    }
/* 156:156 */    return -1;
/* 157:    */  }
/* 158:    */  
/* 159:    */  public int lastIndexOf(long k) {
/* 160:160 */    LongListIterator i = listIterator(size());
/* 161:    */    
/* 162:162 */    while (i.hasPrevious()) {
/* 163:163 */      long e = i.previousLong();
/* 164:164 */      if (k == e) return i.nextIndex();
/* 165:    */    }
/* 166:166 */    return -1;
/* 167:    */  }
/* 168:    */  
/* 169:    */  public void size(int size) {
/* 170:170 */    int i = size();
/* 171:171 */    for (size <= i; i++ < size; add(0L)) {}
/* 172:172 */    while (i-- != size) remove(i);
/* 173:    */  }
/* 174:    */  
/* 175:    */  public LongList subList(int from, int to)
/* 176:    */  {
/* 177:177 */    ensureIndex(from);
/* 178:178 */    ensureIndex(to);
/* 179:179 */    if (from > to) { throw new IndexOutOfBoundsException(new StringBuilder().append("Start index (").append(from).append(") is greater than end index (").append(to).append(")").toString());
/* 180:    */    }
/* 181:181 */    return new LongSubList(this, from, to);
/* 182:    */  }
/* 183:    */  
/* 185:    */  @Deprecated
/* 186:    */  public LongList longSubList(int from, int to)
/* 187:    */  {
/* 188:188 */    return subList(from, to);
/* 189:    */  }
/* 190:    */  
/* 200:    */  public void removeElements(int from, int to)
/* 201:    */  {
/* 202:202 */    ensureIndex(to);
/* 203:203 */    LongListIterator i = listIterator(from);
/* 204:204 */    int n = to - from;
/* 205:205 */    if (n < 0) throw new IllegalArgumentException(new StringBuilder().append("Start index (").append(from).append(") is greater than end index (").append(to).append(")").toString());
/* 206:206 */    while (n-- != 0) {
/* 207:207 */      i.nextLong();
/* 208:208 */      i.remove();
/* 209:    */    }
/* 210:    */  }
/* 211:    */  
/* 222:    */  public void addElements(int index, long[] a, int offset, int length)
/* 223:    */  {
/* 224:224 */    ensureIndex(index);
/* 225:225 */    if (offset < 0) throw new ArrayIndexOutOfBoundsException(new StringBuilder().append("Offset (").append(offset).append(") is negative").toString());
/* 226:226 */    if (offset + length > a.length) throw new ArrayIndexOutOfBoundsException(new StringBuilder().append("End index (").append(offset + length).append(") is greater than array length (").append(a.length).append(")").toString());
/* 227:227 */    while (length-- != 0) add(index++, a[(offset++)]);
/* 228:    */  }
/* 229:    */  
/* 230:    */  public void addElements(int index, long[] a) {
/* 231:231 */    addElements(index, a, 0, a.length);
/* 232:    */  }
/* 233:    */  
/* 244:    */  public void getElements(int from, long[] a, int offset, int length)
/* 245:    */  {
/* 246:246 */    LongListIterator i = listIterator(from);
/* 247:247 */    if (offset < 0) throw new ArrayIndexOutOfBoundsException(new StringBuilder().append("Offset (").append(offset).append(") is negative").toString());
/* 248:248 */    if (offset + length > a.length) throw new ArrayIndexOutOfBoundsException(new StringBuilder().append("End index (").append(offset + length).append(") is greater than array length (").append(a.length).append(")").toString());
/* 249:249 */    if (from + length > size()) throw new IndexOutOfBoundsException(new StringBuilder().append("End index (").append(from + length).append(") is greater than list size (").append(size()).append(")").toString());
/* 250:250 */    while (length-- != 0) a[(offset++)] = i.nextLong();
/* 251:    */  }
/* 252:    */  
/* 253:    */  private boolean valEquals(Object a, Object b)
/* 254:    */  {
/* 255:255 */    return a == null ? false : b == null ? true : a.equals(b);
/* 256:    */  }
/* 257:    */  
/* 258:    */  public boolean equals(Object o)
/* 259:    */  {
/* 260:260 */    if (o == this) return true;
/* 261:261 */    if (!(o instanceof List)) return false;
/* 262:262 */    List<?> l = (List)o;
/* 263:263 */    int s = size();
/* 264:264 */    if (s != l.size()) { return false;
/* 265:    */    }
/* 266:266 */    ListIterator<?> i1 = listIterator();ListIterator<?> i2 = l.listIterator();
/* 267:    */    
/* 271:271 */    while (s-- != 0) if (!valEquals(i1.next(), i2.next())) { return false;
/* 272:    */      }
/* 273:273 */    return true;
/* 274:    */  }
/* 275:    */  
/* 288:    */  public int compareTo(List<? extends Long> l)
/* 289:    */  {
/* 290:290 */    if (l == this) { return 0;
/* 291:    */    }
/* 292:292 */    if ((l instanceof LongList))
/* 293:    */    {
/* 294:294 */      LongListIterator i1 = listIterator();LongListIterator i2 = ((LongList)l).listIterator();
/* 295:    */      
/* 298:298 */      while ((i1.hasNext()) && (i2.hasNext())) {
/* 299:299 */        long e1 = i1.nextLong();
/* 300:300 */        long e2 = i2.nextLong();
/* 301:301 */        int r; if ((r = e1 == e2 ? 0 : e1 < e2 ? -1 : 1) != 0) return r;
/* 302:    */      }
/* 303:303 */      return i1.hasNext() ? 1 : i2.hasNext() ? -1 : 0;
/* 304:    */    }
/* 305:    */    
/* 306:306 */    ListIterator<? extends Long> i1 = listIterator();ListIterator<? extends Long> i2 = l.listIterator();
/* 307:    */    
/* 309:309 */    while ((i1.hasNext()) && (i2.hasNext())) { int r;
/* 310:310 */      if ((r = ((Comparable)i1.next()).compareTo(i2.next())) != 0) return r;
/* 311:    */    }
/* 312:312 */    return i1.hasNext() ? 1 : i2.hasNext() ? -1 : 0;
/* 313:    */  }
/* 314:    */  
/* 319:    */  public int hashCode()
/* 320:    */  {
/* 321:321 */    LongIterator i = iterator();
/* 322:322 */    int h = 1;int s = size();
/* 323:323 */    while (s-- != 0) {
/* 324:324 */      long k = i.nextLong();
/* 325:325 */      h = 31 * h + HashCommon.long2int(k);
/* 326:    */    }
/* 327:327 */    return h;
/* 328:    */  }
/* 329:    */  
/* 330:    */  public void push(long o)
/* 331:    */  {
/* 332:332 */    add(o);
/* 333:    */  }
/* 334:    */  
/* 335:    */  public long popLong() {
/* 336:336 */    if (isEmpty()) throw new NoSuchElementException();
/* 337:337 */    return removeLong(size() - 1);
/* 338:    */  }
/* 339:    */  
/* 340:    */  public long topLong() {
/* 341:341 */    if (isEmpty()) throw new NoSuchElementException();
/* 342:342 */    return getLong(size() - 1);
/* 343:    */  }
/* 344:    */  
/* 345:    */  public long peekLong(int i) {
/* 346:346 */    return getLong(size() - 1 - i);
/* 347:    */  }
/* 348:    */  
/* 350:    */  public boolean rem(long k)
/* 351:    */  {
/* 352:352 */    int index = indexOf(k);
/* 353:353 */    if (index == -1) return false;
/* 354:354 */    removeLong(index);
/* 355:355 */    return true;
/* 356:    */  }
/* 357:    */  
/* 358:    */  public boolean remove(Object o)
/* 359:    */  {
/* 360:360 */    return rem(((Long)o).longValue());
/* 361:    */  }
/* 362:    */  
/* 363:    */  public boolean addAll(int index, LongCollection c)
/* 364:    */  {
/* 365:365 */    return addAll(index, c);
/* 366:    */  }
/* 367:    */  
/* 368:    */  public boolean addAll(int index, LongList l)
/* 369:    */  {
/* 370:370 */    return addAll(index, l);
/* 371:    */  }
/* 372:    */  
/* 373:    */  public boolean addAll(LongCollection c) {
/* 374:374 */    return addAll(size(), c);
/* 375:    */  }
/* 376:    */  
/* 377:    */  public boolean addAll(LongList l) {
/* 378:378 */    return addAll(size(), l);
/* 379:    */  }
/* 380:    */  
/* 381:    */  public void add(int index, Long ok)
/* 382:    */  {
/* 383:383 */    add(index, ok.longValue());
/* 384:    */  }
/* 385:    */  
/* 386:    */  public Long set(int index, Long ok)
/* 387:    */  {
/* 388:388 */    return Long.valueOf(set(index, ok.longValue()));
/* 389:    */  }
/* 390:    */  
/* 391:    */  public Long get(int index)
/* 392:    */  {
/* 393:393 */    return Long.valueOf(getLong(index));
/* 394:    */  }
/* 395:    */  
/* 396:    */  public int indexOf(Object ok)
/* 397:    */  {
/* 398:398 */    return indexOf(((Long)ok).longValue());
/* 399:    */  }
/* 400:    */  
/* 401:    */  public int lastIndexOf(Object ok)
/* 402:    */  {
/* 403:403 */    return lastIndexOf(((Long)ok).longValue());
/* 404:    */  }
/* 405:    */  
/* 406:    */  public Long remove(int index)
/* 407:    */  {
/* 408:408 */    return Long.valueOf(removeLong(index));
/* 409:    */  }
/* 410:    */  
/* 411:    */  public void push(Long o)
/* 412:    */  {
/* 413:413 */    push(o.longValue());
/* 414:    */  }
/* 415:    */  
/* 416:    */  public Long pop()
/* 417:    */  {
/* 418:418 */    return Long.valueOf(popLong());
/* 419:    */  }
/* 420:    */  
/* 421:    */  public Long top()
/* 422:    */  {
/* 423:423 */    return Long.valueOf(topLong());
/* 424:    */  }
/* 425:    */  
/* 426:    */  public Long peek(int i)
/* 427:    */  {
/* 428:428 */    return Long.valueOf(peekLong(i));
/* 429:    */  }
/* 430:    */  
/* 433:    */  public String toString()
/* 434:    */  {
/* 435:435 */    StringBuilder s = new StringBuilder();
/* 436:436 */    LongIterator i = iterator();
/* 437:437 */    int n = size();
/* 438:    */    
/* 439:439 */    boolean first = true;
/* 440:    */    
/* 441:441 */    s.append("[");
/* 442:    */    
/* 443:443 */    while (n-- != 0) {
/* 444:444 */      if (first) first = false; else
/* 445:445 */        s.append(", ");
/* 446:446 */      long k = i.nextLong();
/* 447:    */      
/* 450:450 */      s.append(String.valueOf(k));
/* 451:    */    }
/* 452:    */    
/* 453:453 */    s.append("]");
/* 454:454 */    return s.toString();
/* 455:    */  }
/* 456:    */  
/* 458:    */  public static class LongSubList
/* 459:    */    extends AbstractLongList
/* 460:    */    implements Serializable
/* 461:    */  {
/* 462:    */    public static final long serialVersionUID = -7046029254386353129L;
/* 463:    */    protected final LongList l;
/* 464:    */    protected final int from;
/* 465:    */    protected int to;
/* 466:    */    private static final boolean ASSERTS = false;
/* 467:    */    
/* 468:    */    public LongSubList(LongList l, int from, int to)
/* 469:    */    {
/* 470:470 */      this.l = l;
/* 471:471 */      this.from = from;
/* 472:472 */      this.to = to;
/* 473:    */    }
/* 474:    */    
/* 478:    */    private void assertRange() {}
/* 479:    */    
/* 482:    */    public boolean add(long k)
/* 483:    */    {
/* 484:484 */      this.l.add(this.to, k);
/* 485:485 */      this.to += 1;
/* 486:    */      
/* 487:487 */      return true;
/* 488:    */    }
/* 489:    */    
/* 490:    */    public void add(int index, long k) {
/* 491:491 */      ensureIndex(index);
/* 492:492 */      this.l.add(this.from + index, k);
/* 493:493 */      this.to += 1;
/* 494:    */    }
/* 495:    */    
/* 496:    */    public boolean addAll(int index, Collection<? extends Long> c)
/* 497:    */    {
/* 498:498 */      ensureIndex(index);
/* 499:499 */      this.to += c.size();
/* 500:    */      
/* 505:505 */      return this.l.addAll(this.from + index, c);
/* 506:    */    }
/* 507:    */    
/* 508:    */    public long getLong(int index) {
/* 509:509 */      ensureRestrictedIndex(index);
/* 510:510 */      return this.l.getLong(this.from + index);
/* 511:    */    }
/* 512:    */    
/* 513:    */    public long removeLong(int index) {
/* 514:514 */      ensureRestrictedIndex(index);
/* 515:515 */      this.to -= 1;
/* 516:516 */      return this.l.removeLong(this.from + index);
/* 517:    */    }
/* 518:    */    
/* 519:    */    public long set(int index, long k) {
/* 520:520 */      ensureRestrictedIndex(index);
/* 521:521 */      return this.l.set(this.from + index, k);
/* 522:    */    }
/* 523:    */    
/* 524:    */    public void clear() {
/* 525:525 */      removeElements(0, size());
/* 526:    */    }
/* 527:    */    
/* 528:    */    public int size()
/* 529:    */    {
/* 530:530 */      return this.to - this.from;
/* 531:    */    }
/* 532:    */    
/* 533:    */    public void getElements(int from, long[] a, int offset, int length) {
/* 534:534 */      ensureIndex(from);
/* 535:535 */      if (from + length > size()) throw new IndexOutOfBoundsException("End index (" + from + length + ") is greater than list size (" + size() + ")");
/* 536:536 */      this.l.getElements(this.from + from, a, offset, length);
/* 537:    */    }
/* 538:    */    
/* 539:    */    public void removeElements(int from, int to) {
/* 540:540 */      ensureIndex(from);
/* 541:541 */      ensureIndex(to);
/* 542:542 */      this.l.removeElements(this.from + from, this.from + to);
/* 543:543 */      this.to -= to - from;
/* 544:    */    }
/* 545:    */    
/* 546:    */    public void addElements(int index, long[] a, int offset, int length)
/* 547:    */    {
/* 548:548 */      ensureIndex(index);
/* 549:549 */      this.l.addElements(this.from + index, a, offset, length);
/* 550:550 */      this.to += length;
/* 551:    */    }
/* 552:    */    
/* 553:    */    public LongListIterator listIterator(final int index)
/* 554:    */    {
/* 555:555 */      ensureIndex(index);
/* 556:    */      
/* 557:557 */      new AbstractLongListIterator() {
/* 558:558 */        int last = -1; int pos = index;
/* 559:    */        
/* 560:560 */        public boolean hasNext() { return this.pos < AbstractLongList.LongSubList.this.size(); }
/* 561:561 */        public boolean hasPrevious() { return this.pos > 0; }
/* 562:562 */        public long nextLong() { if (!hasNext()) throw new NoSuchElementException(); return AbstractLongList.LongSubList.this.l.getLong(AbstractLongList.LongSubList.this.from + (this.last = this.pos++)); }
/* 563:563 */        public long previousLong() { if (!hasPrevious()) throw new NoSuchElementException(); return AbstractLongList.LongSubList.this.l.getLong(AbstractLongList.LongSubList.this.from + (this.last = --this.pos)); }
/* 564:564 */        public int nextIndex() { return this.pos; }
/* 565:565 */        public int previousIndex() { return this.pos - 1; }
/* 566:    */        
/* 567:567 */        public void add(long k) { if (this.last == -1) throw new IllegalStateException();
/* 568:568 */          AbstractLongList.LongSubList.this.add(this.pos++, k);
/* 569:569 */          this.last = -1;
/* 570:    */        }
/* 571:    */        
/* 572:    */        public void set(long k) {
/* 573:573 */          if (this.last == -1) throw new IllegalStateException();
/* 574:574 */          AbstractLongList.LongSubList.this.set(this.last, k);
/* 575:    */        }
/* 576:    */        
/* 577:577 */        public void remove() { if (this.last == -1) throw new IllegalStateException();
/* 578:578 */          AbstractLongList.LongSubList.this.removeLong(this.last);
/* 579:    */          
/* 580:580 */          if (this.last < this.pos) this.pos -= 1;
/* 581:581 */          this.last = -1;
/* 582:    */        }
/* 583:    */      };
/* 584:    */    }
/* 585:    */    
/* 586:    */    public LongList subList(int from, int to)
/* 587:    */    {
/* 588:588 */      ensureIndex(from);
/* 589:589 */      ensureIndex(to);
/* 590:590 */      if (from > to) { throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
/* 591:    */      }
/* 592:592 */      return new LongSubList(this, from, to);
/* 593:    */    }
/* 594:    */    
/* 596:    */    public boolean rem(long k)
/* 597:    */    {
/* 598:598 */      int index = indexOf(k);
/* 599:599 */      if (index == -1) return false;
/* 600:600 */      this.to -= 1;
/* 601:601 */      this.l.removeLong(this.from + index);
/* 602:    */      
/* 603:603 */      return true;
/* 604:    */    }
/* 605:    */    
/* 606:    */    public boolean remove(Object o) {
/* 607:607 */      return rem(((Long)o).longValue());
/* 608:    */    }
/* 609:    */    
/* 610:    */    public boolean addAll(int index, LongCollection c) {
/* 611:611 */      ensureIndex(index);
/* 612:612 */      this.to += c.size();
/* 613:    */      
/* 618:618 */      return this.l.addAll(this.from + index, c);
/* 619:    */    }
/* 620:    */    
/* 621:    */    public boolean addAll(int index, LongList l) {
/* 622:622 */      ensureIndex(index);
/* 623:623 */      this.to += l.size();
/* 624:    */      
/* 629:629 */      return this.l.addAll(this.from + index, l);
/* 630:    */    }
/* 631:    */  }
/* 632:    */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     it.unimi.dsi.fastutil.longs.AbstractLongList
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */