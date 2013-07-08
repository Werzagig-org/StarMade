/*  1:   */package org.lwjgl.opengl;
/*  2:   */
/*  3:   */import java.nio.IntBuffer;
/*  4:   */import org.lwjgl.BufferChecks;
/*  5:   */import org.lwjgl.MemoryUtil;
/*  6:   */
/* 13:   */public final class ATIDrawBuffers
/* 14:   */{
/* 15:   */  public static final int GL_MAX_DRAW_BUFFERS_ATI = 34852;
/* 16:   */  public static final int GL_DRAW_BUFFER0_ATI = 34853;
/* 17:   */  public static final int GL_DRAW_BUFFER1_ATI = 34854;
/* 18:   */  public static final int GL_DRAW_BUFFER2_ATI = 34855;
/* 19:   */  public static final int GL_DRAW_BUFFER3_ATI = 34856;
/* 20:   */  public static final int GL_DRAW_BUFFER4_ATI = 34857;
/* 21:   */  public static final int GL_DRAW_BUFFER5_ATI = 34858;
/* 22:   */  public static final int GL_DRAW_BUFFER6_ATI = 34859;
/* 23:   */  public static final int GL_DRAW_BUFFER7_ATI = 34860;
/* 24:   */  public static final int GL_DRAW_BUFFER8_ATI = 34861;
/* 25:   */  public static final int GL_DRAW_BUFFER9_ATI = 34862;
/* 26:   */  public static final int GL_DRAW_BUFFER10_ATI = 34863;
/* 27:   */  public static final int GL_DRAW_BUFFER11_ATI = 34864;
/* 28:   */  public static final int GL_DRAW_BUFFER12_ATI = 34865;
/* 29:   */  public static final int GL_DRAW_BUFFER13_ATI = 34866;
/* 30:   */  public static final int GL_DRAW_BUFFER14_ATI = 34867;
/* 31:   */  public static final int GL_DRAW_BUFFER15_ATI = 34868;
/* 32:   */  
/* 33:   */  public static void glDrawBuffersATI(IntBuffer buffers)
/* 34:   */  {
/* 35:35 */    ContextCapabilities caps = GLContext.getCapabilities();
/* 36:36 */    long function_pointer = caps.glDrawBuffersATI;
/* 37:37 */    BufferChecks.checkFunctionAddress(function_pointer);
/* 38:38 */    BufferChecks.checkDirect(buffers);
/* 39:39 */    nglDrawBuffersATI(buffers.remaining(), MemoryUtil.getAddress(buffers), function_pointer);
/* 40:   */  }
/* 41:   */  
/* 42:   */  static native void nglDrawBuffersATI(int paramInt, long paramLong1, long paramLong2);
/* 43:   */  
/* 44:   */  public static void glDrawBuffersATI(int buffer) {
/* 45:45 */    ContextCapabilities caps = GLContext.getCapabilities();
/* 46:46 */    long function_pointer = caps.glDrawBuffersATI;
/* 47:47 */    BufferChecks.checkFunctionAddress(function_pointer);
/* 48:48 */    nglDrawBuffersATI(1, APIUtil.getInt(caps, buffer), function_pointer);
/* 49:   */  }
/* 50:   */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     org.lwjgl.opengl.ATIDrawBuffers
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */