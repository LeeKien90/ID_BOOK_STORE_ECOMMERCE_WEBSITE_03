package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import ra.model.entity.ResetPassword;
import ra.model.repository.ResetPasswordRepository;
import ra.model.service.ResetPasswordService;

public class ResetPasswordServiceImp implements ResetPasswordService {
    @Autowired
    private ResetPasswordRepository resetPasswordRepository;
    @Override
    public ResetPassword saveOfUpdate(ResetPassword resetPassword) {
        return resetPasswordRepository.save(resetPassword);
    }

    @Override
    public ResetPassword getLastTokenByUserId(int userId) {
        return resetPasswordRepository.getLastTokenByUserId(userId);
    }
}
