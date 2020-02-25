import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegisterComponent } from './register/register.component';
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
import { CheckReviewsComponent } from './check-reviews/check-reviews.component';
import { CoauthorsComponent } from './coauthors/coauthors.component';
import { ReviewerComponent } from './reviewer/reviewer.component';
import { SetDateComponent } from './set-date/set-date.component';

const routes: Routes = [

  {path: '', component: HomeComponent}, 
  {path: 'register/:mode', component: RegisterComponent},
  {path: 'unosOblasti', component: UnosOblastiComponent},
  {path: 'unosOblasti/:mode', component: UnosOblastiComponent},
  {path: 'login', component: LoginComponent},
  {path: 'admin', component: AdminComponent},
  {path: 'editor', component: EditorComponent},
  {path: 'editor/:mode', component: EditorComponent},
  {path: 'admin', component: AdminComponent},
  {path: 'checkData', component: CheckDataComponent},
  {path: 'magazines', component: MagazinesComponent},
  {path: 'home', component: HomeComponent},
  {path: 'userProfile', component: UserProfileComponent},
  {path: 'chooseMagazine', component: ChooseMagazineComponent},
  {path: 'chooseMagazine/:mode', component: ChooseMagazineComponent},
  {path: 'mainEditor', component: MainEditorPageComponent},
  {path: 'mainEditor/:mode', component: MainEditorPageComponent},
  {path: 'mainEditor/:mode/:type', component: MainEditorPageComponent},
  {path: 'paying', component: PayingComponent},
  {path: 'profil', component: UserProfileComponent},
  {path: 'prepareReviewers', component: PrepareReviewersComponent},
  {path: 'prepareReviewers/:mode', component: PrepareReviewersComponent},
  {path: 'prepareReviewers/:mode/:type', component: PrepareReviewersComponent},
  {path: 'review', component: ReviewComponent},
  {path: 'checkReviews', component: CheckReviewsComponent},
  {path: 'checkReviews/:mode', component: CheckReviewsComponent},
  {path: 'addCoauthors', component: CoauthorsComponent},
  {path: 'reviewers', component: ReviewerComponent},
  {path: 'setDate', component: SetDateComponent}
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
