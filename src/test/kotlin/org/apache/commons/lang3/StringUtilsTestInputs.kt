package org.apache.commons.lang3

import io.kotest.core.Tuple4
import io.kotest.matchers.shouldBe
import io.kotest.core.spec.style.FreeSpec
class StringUtilsTestNew : FreeSpec({
    "Capitalization tests" - {
        listOf(
            "" to "",
            "x" to "X",
            StringUtilsTestInputs.FOO_CAP to StringUtilsTestInputs.FOO_CAP,
            StringUtilsTestInputs.FOO_UNCAP to StringUtilsTestInputs.FOO_CAP,
            "\u01C9" to "\u01C8",
            "cat" to "Cat",
            "cAt" to "CAt",
            "'cat'" to "'cat'",
            "" to "",
            null to null,

        ).forEach{ (input, expected) ->
            "it should capitalize '$input' to '$expected'"{
                StringUtils.capitalize(input) shouldBe expected
            }
        }
    }

    "text centering tests" - {
        listOf(
            Triple("",4,"    "),
            Triple("ab",0,"ab"),
            Triple("cd",-1,"cd"),
            Triple("ef",1,"ef"),
            Triple("ab",4," ab "),
            Triple("abcd",2,"abcd"),
            Triple("x",4," x  "),
            Triple("y",5,"  y  "),
        ).forEach { (input, size, expected) ->
            " center('$input', $size) should be '$expected'" {
                StringUtils.center(input, size) shouldBe expected
            }
        }
    }

    "text centering with padChar tests" - {
        listOf(
            Tuple4("",4,'x',"xxxx"),
            Tuple4("ab",0,' ',"ab"),
            Tuple4("cd",-1,' ',"cd"),
            Tuple4("ef",1,'x',"ef"),
            Tuple4("ab",4,'x',"xabx"),
            Tuple4("abcd",2,'x',"abcd"),
            Tuple4("x",4,' '," x  "),
            Tuple4("y",5,'x',"xxyxx"),
            Tuple4("y",5,' ',"  y  "),
        ).forEach { (input, size, padChar, expected) ->
            " center('$input', $size, '$padChar') should be '$expected'" {
                StringUtils.center(input, size, padChar) shouldBe expected
            }
        }
    }

    "text centering with pad string " - {
        listOf(
            Tuple4("",4," ","    "),
            Tuple4("ab",0," ","ab"),
            Tuple4("cd",-1," ","cd"),
            Tuple4("ef",1," ","ef"),
            Tuple4("ab",4," "," ab "),
            Tuple4("abcd",2,"x","abcd"),
            Tuple4("x",4," "," x  "),
            Tuple4("y",5,"x","xxyxx"),
            Tuple4("a",4,"yz","yayz"),
            Tuple4("a",7,"yz","yzyayzy"),
            Tuple4("abc",7,null,"  abc  "),
            Tuple4("abc",7,"","  abc  "),
        ).forEach { (input, size, padStr, expected) ->
            " center('$input', $size, '$padStr') should be '$expected'" {
                StringUtils.center(input, size, padStr) shouldBe expected
            }
        }
    }
})

 object StringUtilsTestInputs {
     private val ws = StringBuilder()
     private val nws = java.lang.StringBuilder()
     private const val hs =  160.toChar().toString()
     private val tr = StringBuilder()
     private val ntr = StringBuilder()

        private const val CHAR_MAX: Int = java.lang.Character.MAX_VALUE.code

        init {
            println("Beginning init block")
            for (i in 0..CHAR_MAX) {
                if (Character.isWhitespace(Char(i))) {
                    ws.append(i.toChar().toString())
                    if (i > 32) {
                        ntr.append(i.toChar().toString())
                    }
                } else if (i < 40) {
                    nws.append(i.toChar().toString())
                }
            }

            for (i in 0..32) {
                tr.append(i.toChar().toString())
            }
        }

        val WHITESPACE = ws.toString()
        val NON_WHITESPACE = nws.toString()
        const val HARD_SPACE = hs
        val TRIMMABLE = tr.toString()

        val ARRAY_LIST = arrayOf("foo", "bar", "baz")
        val EMPTY_ARRAY_LIST = arrayOf<String>()
        val NULL_ARRAY_LIST = arrayOf<String?>(null)
        val NULL_TO_STRING_LIST = arrayOf<Any>(object : Any() {
            override fun toString(): String {
                return null as String
            }
        })
        val MIXED_ARRAY_LIST = arrayOf<String?>(null, "", "foo")
        val MIXED_TYPE_LIST = arrayOf<Any>("foo", java.lang.Long.valueOf(2L))
        val LONG_PRIM_LIST = arrayOf<Long>(1, 2)
        val INT_PRIM_LIST = arrayOf<Int>(1, 2)
        val BYTE_PRIM_LIST = arrayOf<Byte>(1, 2)
        val SHORT_PRIM_LIST = arrayOf<Short>(1, 2)
        val CHAR_PRIM_LIST = arrayOf<Char>('1', '2')
        val FLOAT_PRIM_LIST = arrayOf<Float>(1f, 2f)
        val DOUBLE_PRIM_LIST = arrayOf<Double>(1.0, 2.0)

        val MIXED_STRING_LIST = listOf(null, "", "foo")
        val MIXED_TYPE_OBJECT_LIST = listOf("foo", java.lang.Long.valueOf(2))
        val STRING_LIST = listOf("foo", "bar", "baz")
        val EMPTY_STRING_LIST = listOf<String>()
        val NULL_STRING_LIST = listOf<String?>(null)

     const val SEPARATOR = ","
     const val SEPARATOR_CHAR = ';'
     const val COMMA_SEPARATOR_CHAR = ','
     const val TEXT_LSIT = "foo,bar,baz"
     const val TEXT_LIST_CHAR = "foo;bar;baz"
     const val TEXT_LIST_NOSEP = "foobarbaz"
     const val FOO_UNCAP = "foo"
     const val FOO_CAP = "Foo"
        const val SENTENCE_UNCAP = "foo bar baz"
        const val SENTENCE_CAP = "Foo Bar Baz"
        val EMPTY = arrayOf<Boolean>()
        val ARRAY_FALSE_FALSE = arrayOf<Boolean>(false, false)
        val ARRAY_FALSE_TRUE = arrayOf<Boolean>(false, true)
        val ARRAY_FALSE_TRUE_FALSE = arrayOf<Boolean>(false, true, false)
 }

