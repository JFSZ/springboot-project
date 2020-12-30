package com.zz.springbootproject.demo.leetcode;

import java.util.Arrays;
import java.util.Objects;

/**
 * @Description: leetcode 删除排序数组中的重复项
 * @Author: chenxue
 * @Date: 2020-10-09 19:18
 */
public class Solution {

    /**
     * @Description:
     * 给定一个排序数组，你需要在 原地 删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
     * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
     * 给定数组 nums = [1,1,2],
     * 函数应该返回新的长度 2, 并且原数组 nums 的前两个元素被修改为 1, 2。
     * 你不需要考虑数组中超出新长度后面的元素。
     *
     * 给定 nums = [0,0,1,1,1,2,2,3,3,4],
     *
     * 函数应该返回新的长度 5, 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4。
     *
     * 你不需要考虑数组中超出新长度后面的元素。
     *
     *
     * 为什么返回数值是整数，但输出的答案是数组呢?
     *
     * 请注意，输入数组是以「引用」方式传递的，这意味着在函数里修改输入数组对于调用者是可见的。
     *
     * 你可以想象内部操作如下:
     *
     * // nums 是以“引用”方式传递的。也就是说，不对实参做任何拷贝
     * int len = removeDuplicates(nums);
     *
     * // 在函数里修改输入数组对于调用者是可见的。
     * // 根据你的函数返回的长度, 它会打印出数组中该长度范围内的所有元素。
     * for (int i = 0; i < len; i++) {
     *     print(nums[i]);
     * }
     * @param nums
     * @Author: chenxue
     * @Date: 2020-10-09 19:19
     **/
    public int removeDuplicates(int[] nums) {
        int j = 0;
        if (Objects.isNull(nums) || nums.length == 0) {
            return j;
        }
        if (Objects.nonNull(nums)) {
            for (int i = 1; i < nums.length; i++) {
                if (nums[i] != nums[j]) {
                    nums[++j] = nums[i];
                }
            }
        }

        return j + 1;
    }


    /**
     * @Description:
     * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
     *
     * 不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
     *
     * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
     * 示例 1:
     *
     * 给定 nums = [3,2,2,3], val = 3,
     *
     * 函数应该返回新的长度 2, 并且 nums 中的前两个元素均为 2。
     *
     * 你不需要考虑数组中超出新长度后面的元素。
     * 示例 2:
     *
     * 给定 nums = [0,1,2,2,3,0,4,2], val = 2,
     *
     * 函数应该返回新的长度 5, 并且 nums 中的前五个元素为 0, 1, 3, 0, 4。
     *
     * 注意这五个元素可为任意顺序。
     *
     * 你不需要考虑数组中超出新长度后面的元素。
     *
     * @param
     * @Author: chenxue
     * @Date: 2020-10-14 9:19
     * @Exception /throws
     * @since
     */
    public int work1(int[] nums, int val){
        int j = 0;
        if(Objects.isNull(nums) || nums.length == 0 ) {
            return j;
        }
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] != val) {
                nums[j] = nums[i];
                j ++;
            }
        }
        System.out.println(Arrays.toString(nums));
        return j;
    }

    /**
     * @Description:
     * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
     * 你可以假设数组中无重复元素。
     * 示例 1:
     * 输入: [1,3,5,6], 5
     * 输出: 2
     * 示例 2:
     *
     * 输入: [1,3,5,6], 2
     * 输出: 1
     * 示例 3:
     *
     * 输入: [1,3,5,6], 7
     * 输出: 4
     * 示例 4:
     *
     * 输入: [1,3,5,6], 0
     * 输出: 0
     *
     * @param
     * @Author: chenxue
     * @Date: 2020-10-15 11:17
     * @Exception /throws
     * @since
     */
    public int search(int[] nums, int target) {
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if(target > nums[i]) {
                j++;
            }
        }
        return j;
    }

    /**
     * @Description: 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     * 示例:
     * 输入: [-2,1,-3,4,-1,2,1,-5,4]
     * 输出: 6
     * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
     *
     * @param nums
     * @Author: chenxue
     * @Date: 2020-10-21 17:41
     * @throws
     */
    public int maxSubArray(int[] nums) {
        int max = nums[0];
        if(Objects.nonNull(nums) && nums.length == 1) {
            return max;
        }
        for (int i = 0; i < nums.length; i++) {
            int temp = 0;
            for (int j = i; j < nums.length; j++) {
                max = Math.max(max, temp += nums[j]);
            }
        }
        return max;
    }

    /**
     * @Description: 给定一个由整数组成的非空数组所表示的非负整数，在该数的基础上加一。
     *
     * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
     *
     * 你可以假设除了整数 0 之外，这个整数不会以零开头。
     *
     * 示例 1:
     *
     * 输入: [1,2,3]
     * 输出: [1,2,4]
     * 解释: 输入数组表示数字 123。
     * 示例 2:
     *
     * 输入: [4,3,2,1]
     * 输出: [4,3,2,2]
     * 解释: 输入数组表示数字 4321。
     *
     * @param digits
     * @Author: chenxue
     * @Date: 2020-11-05 10:56
     * @throws
     */
    public int[] plusOne(int[] digits) {

        return null;
    }


    public void test() throws Exception{

    }

    public static void main(String[] args) {
        int[] nums = {1,3,5,6};
        Solution solution = new Solution();
        int i = solution.search(nums,2);
        System.out.println(i);
    }

}
