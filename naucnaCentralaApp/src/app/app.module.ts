import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { RouterModule} from '@angular/router';
import { AppComponent } from './app.component';
import { RegisterComponent } from './register/register.component';
import { HttpClientModule } from '@angular/common/http';
import { HttpModule } from '@angular/http';
import { FormsModule, ReactiveFormsModule }   from '@angular/forms';
import { NavigationComponent } from './navigation/navigation.component';
import { UnosOblastiComponent } from './unos-oblasti/unos-oblasti.component';
import { LoginComponent } from './login/login.component';
import { AdminComponent } from './admin/admin.component';
import { HomeComponent } from './home/home.component';
import { EditorComponent } from './editor/editor.component';
import { AddEditorsComponent } from './add-editors/add-editors.component';
import { CheckDataComponent } from './check-data/check-data.component';
import { MagazinesComponent } from './magazines/magazines.component';
import { ChooseMagazineComponent } from './choose-magazine/choose-magazine.component';
import { MainEditorPageComponent } from './main-editor-page/main-editor-page.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { PayingComponent } from './paying/paying.component';
import { PrepareReviewersComponent } from './prepare-reviewers/prepare-reviewers.component';
import { ReviewComponent } from './review/review.component';
import { UserService } from './services/user.service';
import { MagazineService } from './services/magazine.service';
import { PaperService } from './services/paper.service';
import { AdminServiceService } from './services/admin-service.service';
import { EditorService } from './services/editor.service';
import { RepositoryService } from './services/repository.service';
import { ReviewService } from './services/review.service';
import { CheckReviewsComponent } from './check-reviews/check-reviews.component';
import { CoauthorsComponent } from './coauthors/coauthors.component';
import { ReviewerComponent } from './reviewer/reviewer.component';
import { SetDateComponent } from './set-date/set-date.component';

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    NavigationComponent,
    UnosOblastiComponent,
    LoginComponent,
    AdminComponent,
    HomeComponent,
    EditorComponent,
    AddEditorsComponent,
    CheckDataComponent,
    MagazinesComponent,
    ChooseMagazineComponent,
    MainEditorPageComponent,
    UserProfileComponent,
    PayingComponent,
    PrepareReviewersComponent,
    ReviewComponent,
    CheckReviewsComponent,
    CoauthorsComponent,
    ReviewerComponent,
    SetDateComponent
  ],
  imports: [
    BrowserModule,
    RouterModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule, 
    HttpModule,
    AppRoutingModule
  ],
  providers: [
    UserService,
    MagazineService,
    PaperService,
    AdminServiceService,
    EditorService,
    RepositoryService,
    ReviewService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
