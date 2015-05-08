package com.quantasnet.qlan2.admin.info;

import com.quantasnet.qlan2.security.HasAdminRole;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by andrewlandsverk on 5/7/15.
 */
@Controller
@HasAdminRole
@RequestMapping("/admin/")
public class InfoController {

    @RequestMapping("/system")
    public String system() {
        return "admin/system";
    }

}
