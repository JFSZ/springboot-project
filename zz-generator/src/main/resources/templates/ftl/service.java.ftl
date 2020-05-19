package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import com.zz.springbootproject.utils.PageUtil;
import java.util.Map;

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {
 PageUtil queryPage(Map<String, Object> params);
}
</#if>
