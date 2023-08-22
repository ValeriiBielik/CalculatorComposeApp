package com.bielik.calculator.domain

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ExpressionParserTest {

    private lateinit var parser: ExpressionParser

    @Test
    fun `Simple expression is properly parsed`() {
        parser = ExpressionParser("3+5-3x4/3")

        val actual = parser.parse()

        val expected = listOf(
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.ADD),
            ExpressionPart.Number(5.0),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Number(4.0),
            ExpressionPart.Op(Operation.DIVIDE),
            ExpressionPart.Number(3.0)
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Expression with parentheses is properly parsed`() {
        parser = ExpressionParser("4-(4x5)")

        val actual = parser.parse()

        val expected = listOf(
            ExpressionPart.Number(4.0),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Parentheses(ParenthesesType.Opening),
            ExpressionPart.Number(4.0),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Number(5.0),
            ExpressionPart.Parentheses(ParenthesesType.Closing)
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Expression with multiple parentheses is properly parsed`() {
        parser = ExpressionParser("2-9+(2x2+(2-1)x(9+2))/(2/1-(2+2))")

        val actual = parser.parse()

        val expected = listOf(
            ExpressionPart.Number(2.0),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Number(9.0),
            ExpressionPart.Op(Operation.ADD),
            ExpressionPart.Parentheses(ParenthesesType.Opening),
            ExpressionPart.Number(2.0),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Number(2.0),
            ExpressionPart.Op(Operation.ADD),
            ExpressionPart.Parentheses(ParenthesesType.Opening),
            ExpressionPart.Number(2.0),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Number(1.0),
            ExpressionPart.Parentheses(ParenthesesType.Closing),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Parentheses(ParenthesesType.Opening),
            ExpressionPart.Number(9.0),
            ExpressionPart.Op(Operation.ADD),
            ExpressionPart.Number(2.0),
            ExpressionPart.Parentheses(ParenthesesType.Closing),
            ExpressionPart.Parentheses(ParenthesesType.Closing),
            ExpressionPart.Op(Operation.DIVIDE),
            ExpressionPart.Parentheses(ParenthesesType.Opening),
            ExpressionPart.Number(2.0),
            ExpressionPart.Op(Operation.DIVIDE),
            ExpressionPart.Number(1.0),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Parentheses(ParenthesesType.Opening),
            ExpressionPart.Number(2.0),
            ExpressionPart.Op(Operation.ADD),
            ExpressionPart.Number(2.0),
            ExpressionPart.Parentheses(ParenthesesType.Closing),
            ExpressionPart.Parentheses(ParenthesesType.Closing)

        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Expression with big and decimal numbers is properly parsed`() {
        parser = ExpressionParser("423-111.24x0.123")

        val actual = parser.parse()

        val expected = listOf(
            ExpressionPart.Number(423.0),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Number(111.24),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Number(0.123),
        )

        assertThat(actual).isEqualTo(expected)
    }
}