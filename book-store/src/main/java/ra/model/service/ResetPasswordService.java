package ra.model.service;

import ra.model.entity.ResetPassword;

public interface ResetPasswordService {
    ResetPassword saveOfUpdate(ResetPassword resetPassword);
    ResetPassword getLastTokenByUserId(int userId);
}
