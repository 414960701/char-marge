# char-marge
思路：遍历字符串，逐一比较字符，若字符相同，则使用两个指针建立滑动窗口，直到遇见字符不同，
将滑动窗口内字符替换，缩小窗口，并退两格，重新比较。

问题1和问题2类似，区别在于替换成空字符还是前一个字符，因此核心代码实现为同一个方法。

在替换字符串时，若直接使用StringBuilder的相关方法替换，时间复杂度为O（n^2）,可见代码中的CharMerge.oldMerge方法
优化方案，将原字符串转成双向字符链表，通过指针特性可快速截断字符串，时间复杂度为O（n）,可见代码中的CharMerge.merge方法

算法实现在main目录下的CharMerge
测试案例在test目录下的CharMergeTest

