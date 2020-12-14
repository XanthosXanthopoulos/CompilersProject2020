/* This file was generated by SableCC (http://www.sablecc.org/). */

package minipython.analysis;

import minipython.node.*;

public interface Analysis extends Switch
{
    Object getIn(Node node);
    void setIn(Node node, Object in);
    Object getOut(Node node);
    void setOut(Node node, Object out);

    void caseStart(Start node);
    void caseAGoal(AGoal node);
    void caseAFunctionAction(AFunctionAction node);
    void caseAStatementAction(AStatementAction node);
    void caseAFunction(AFunction node);
    void caseAArgument(AArgument node);
    void caseAIfStatement(AIfStatement node);
    void caseAWhileStatement(AWhileStatement node);
    void caseAForStatement(AForStatement node);
    void caseAReturnStatement(AReturnStatement node);
    void caseAPrintStatement(APrintStatement node);
    void caseAAssignStatement(AAssignStatement node);
    void caseAArrayAssignStatement(AArrayAssignStatement node);
    void caseAAssertStatement(AAssertStatement node);
    void caseAFunctionStatement(AFunctionStatement node);
    void caseAAssignOperationAssign(AAssignOperationAssign node);
    void caseAMinusAssignOperationAssign(AMinusAssignOperationAssign node);
    void caseADivAssignOperationAssign(ADivAssignOperationAssign node);
    void caseAIdExpression(AIdExpression node);
    void caseAArrayAccessExpression(AArrayAccessExpression node);
    void caseAValueExpression(AValueExpression node);
    void caseAFuncCallExpression(AFuncCallExpression node);
    void caseAOpenExpression(AOpenExpression node);
    void caseATypeExpression(ATypeExpression node);
    void caseAMinExpression(AMinExpression node);
    void caseAMaxExpression(AMaxExpression node);
    void caseAParExpression(AParExpression node);
    void caseAArrayExpression(AArrayExpression node);
    void caseAAddExpression(AAddExpression node);
    void caseAMinusExpression(AMinusExpression node);
    void caseAMultExpression(AMultExpression node);
    void caseADivExpression(ADivExpression node);
    void caseAModExpression(AModExpression node);
    void caseAPowExpression(APowExpression node);
    void caseALogOpComparison(ALogOpComparison node);
    void caseANotComparison(ANotComparison node);
    void caseACompareComparison(ACompareComparison node);
    void caseATrueComparison(ATrueComparison node);
    void caseAFalseComparison(AFalseComparison node);
    void caseAAndOperationLogical(AAndOperationLogical node);
    void caseAOrOperationLogical(AOrOperationLogical node);
    void caseAGrtComparisonSymbol(AGrtComparisonSymbol node);
    void caseALessComparisonSymbol(ALessComparisonSymbol node);
    void caseAGrtEqComparisonSymbol(AGrtEqComparisonSymbol node);
    void caseALessEqComparisonSymbol(ALessEqComparisonSymbol node);
    void caseANEqComparisonSymbol(ANEqComparisonSymbol node);
    void caseAEqComparisonSymbol(AEqComparisonSymbol node);
    void caseAFunctionCall(AFunctionCall node);
    void caseAFunctionValue(AFunctionValue node);
    void caseANumberValue(ANumberValue node);
    void caseAStringValue(AStringValue node);
    void caseANoneValue(ANoneValue node);
    void caseAIdentifier(AIdentifier node);

    void caseTTab(TTab node);
    void caseTNumber(TNumber node);
    void caseTString(TString node);
    void caseTComment(TComment node);
    void caseTBlank(TBlank node);
    void caseTDef(TDef node);
    void caseTPlus(TPlus node);
    void caseTMinus(TMinus node);
    void caseTDmult(TDmult node);
    void caseTMult(TMult node);
    void caseTDiv(TDiv node);
    void caseTMod(TMod node);
    void caseTLogicPlus(TLogicPlus node);
    void caseTLPar(TLPar node);
    void caseTRPar(TRPar node);
    void caseTLBr(TLBr node);
    void caseTRBr(TRBr node);
    void caseTComma(TComma node);
    void caseTSemi(TSemi node);
    void caseTEqual(TEqual node);
    void caseTNotEqual(TNotEqual node);
    void caseTMinusEqual(TMinusEqual node);
    void caseTDivEqual(TDivEqual node);
    void caseTLess(TLess node);
    void caseTGreater(TGreater node);
    void caseTLessEqual(TLessEqual node);
    void caseTGreaterEqual(TGreaterEqual node);
    void caseTTrue(TTrue node);
    void caseTFalse(TFalse node);
    void caseTNot(TNot node);
    void caseTAnd(TAnd node);
    void caseTOr(TOr node);
    void caseTAssign(TAssign node);
    void caseTOpen(TOpen node);
    void caseTType(TType node);
    void caseTMax(TMax node);
    void caseTMin(TMin node);
    void caseTIf(TIf node);
    void caseTWhile(TWhile node);
    void caseTFor(TFor node);
    void caseTPrint(TPrint node);
    void caseTReturn(TReturn node);
    void caseTDot(TDot node);
    void caseTAssert(TAssert node);
    void caseTIn(TIn node);
    void caseTNone(TNone node);
    void caseTId(TId node);
    void caseEOF(EOF node);
}
