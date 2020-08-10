package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
* ${table.comment!} Mapper 接口
* @author ${author}
* @since ${date}
*/
@Mapper
<#if kotlin>
    interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
    public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {
    List<${entity}> queryPage(IPage<${entity}> page, Map
    <String, Object> params);
    }
</#if>
