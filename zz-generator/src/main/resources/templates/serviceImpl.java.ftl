package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.springbootproject.utils.Query;
import com.zz.springbootproject.utils.PageUtil;
import java.util.List;
import java.util.Map;

/**
 * ${table.comment!} 服务实现类
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {
    @Override
    public PageUtil queryPage(Map<String, Object> params) {
      IPage<${entity}> page = new Query<${entity}>(params).getPage();
      List<${entity}> list = baseMapper.queryPage(page,params);
      return new PageUtil(page.setRecords(list));
   }
}
</#if>
