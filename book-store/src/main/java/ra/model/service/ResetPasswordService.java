package ra.model.service;

import ra.model.entity.ResetPassword;

public interface ResetPasswordService {
    ResetPassword saveOrUpdate(ResetPassword resetPassword);
    ResetPassword getLastTokenByUserId(int userId);
}
