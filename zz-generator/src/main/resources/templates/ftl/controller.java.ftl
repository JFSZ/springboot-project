package ${package.Controller};

import java.util.Arrays;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>
       @Autowired
       private ${className}Service ${classname}Service;

       /**
       * 列表
       */
       @RequestMapping("/list")
       @RequiresPermissions("${moduleName}:${pathName}:list")
       public IPage<${entity}> list(@RequestParam Map<String, Object> params){
       LayuiPage page = ${classname}Service.queryPage(params);

       return page;
       }


       /**
       * 信息
       */
       @RequestMapping("/info/{${pk.attrname}}")
       @RequiresPermissions("${moduleName}:${pathName}:info")
       public R info(@PathVariable("${pk.attrname}") ${pk.attrType} ${pk.attrname}){
       ${className}Entity ${classname} = ${classname}Service.selectById(${pk.attrname});

       return R.ok().put("${classname}", ${classname});
       }

       /**
       * 保存
       */
       @RequestMapping("/save")
       @RequiresPermissions("${moduleName}:${pathName}:save")
       public R save(@RequestBody ${className}Entity ${classname}){
       ${classname}Service.insert(${classname});

       return R.ok();
       }

       /**
       * 修改
       */
       @RequestMapping("/update")
       @RequiresPermissions("${moduleName}:${pathName}:update")
       public R update(@RequestBody ${className}Entity ${classname}){
       ${classname}Service.updateById(${classname});

       return R.ok();
       }

       /**
       * 删除
       */
       @RequestMapping("/delete")
       @RequiresPermissions("${moduleName}:${pathName}:delete")
       public R delete(@RequestBody ${pk.attrType}[] ${pk.attrname}s){
       ${classname}Service.deleteBatchIds(Arrays.asList(${pk.attrname}s));

       return R.ok();
       }

       /**
       * 导出
       */
       @RequestMapping("/export")
       @RequiresPermissions("${moduleName}:${pathName}:export")
       public void export(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
       LayuiPage page = ${classname}Service.queryPage(params);

       ExcelUtils.exportExcelToTarget(response, "${comments}", page.getData(), ${className}Bean.class);
       }
}
</#if>
