/*  1:   */package org.jaxen.expr;
/*  2:   */
/* 27:   */class DefaultLessThanEqualExpr
/* 28:   */  extends DefaultRelationalExpr
/* 29:   */{
/* 30:   */  private static final long serialVersionUID = 7980276649555334242L;
/* 31:   */  
/* 56:   */  DefaultLessThanEqualExpr(Expr lhs, Expr rhs)
/* 57:   */  {
/* 58:58 */    super(lhs, rhs);
/* 59:   */  }
/* 60:   */  
/* 61:   */  public String getOperator()
/* 62:   */  {
/* 63:63 */    return "<=";
/* 64:   */  }
/* 65:   */  
/* 66:   */  protected boolean evaluateDoubleDouble(Double lhs, Double rhs)
/* 67:   */  {
/* 68:68 */    return lhs.compareTo(rhs) <= 0;
/* 69:   */  }
/* 70:   */}


/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     org.jaxen.expr.DefaultLessThanEqualExpr
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */